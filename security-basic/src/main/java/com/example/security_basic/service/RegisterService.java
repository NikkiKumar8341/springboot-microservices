package com.example.security_basic.service;

import com.example.security_basic.dto.AuthRequest;
import com.example.security_basic.email.EmailService;
import com.example.security_basic.entity.*;
import com.example.security_basic.repo.StudentRepo;
import com.example.security_basic.repo.TeacherRepo;
import com.example.security_basic.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegisterService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private EmailService emailService;

    private Map<String, String> otpMap = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Optional<UserRegister> userRegisterOptionalInfo = userRepo.findByEmailId(emailId);
        if (userRegisterOptionalInfo.isPresent()) {
            UserRegister userRegister = userRegisterOptionalInfo.get();
            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(userRegister.getRole());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(adminAuthority);
            return new User(userRegister.getEmailId(), userRegister.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found");
    }

    public String addUser(UserRegister userRegister) {
        String encodedPassword = passwordEncoder.encode(userRegister.getPassword());
        userRegister.setPassword(encodedPassword);



        try {
            UserRegister userReg = userRepo.save(userRegister);
            if (userReg.getRole().equalsIgnoreCase("TEACHER")) {

                Teacher teacher = Teacher.builder()
                        .user(userReg) // Associate the UserRegister object with the Teacher
                        .build();

                teacher.generateTeacherId();



                teacherRepo.save(teacher);
            } else if (userReg.getRole().equalsIgnoreCase("STUDENT")) {


                Student student = Student.builder()
                        .user(userReg)
                        .guardian(new Guardian())
                        .build();

                // Generate and set the student ID
                student.generateStudentId();

                studentRepo.save(student);
                // Send registration confirmation email

//                emailService.sendRegistrationEmail(userRegister.getEmailId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


        return "registration successful, check the mail";
    }


    public Map<String, Object> login(AuthRequest authRequest) {
        Optional<UserRegister> userRegisterOptional = userRepo.findByEmailId(authRequest.getEmailId());
        Map<String, Object> response = new HashMap<>();

        if (userRegisterOptional.isPresent()) {
            UserRegister user = userRegisterOptional.get();
            Boolean isPasswordMatch = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());


            if (isPasswordMatch) {
                List<String> role = new ArrayList<>();
                role.add(user.getRole());

                // Generate both access token and refresh token
                String accessToken = jwtService.generateToken(user.getEmailId(), role);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmailId());



                // Store the refresh token securely on the client-side
                // You can choose how to send it to the client, here I'm including it in the response
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken.getToken());
                response.put("role", role.get(0));

                if ("TEACHER".equals(user.getRole())) {
                    String teacherId=teacherRepo.findTeacherIdByUserId(user.getUserId());
                    response.put("teacherId",teacherId);
                } else if ("STUDENT".equals(user.getRole())) {
                    String studentId=studentRepo.findStudentIdByUserId(user.getUserId());
                    response.put("studentId",studentId);
                }





                return response;
            }
            throw new RuntimeException("Password Incorrect");
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    public Map<String, Object> refreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
        Map<String, Object> response = new HashMap<>();

        if (refreshTokenOptional.isPresent()) {
            RefreshToken validRefreshToken = refreshTokenService.verifyExpiration(refreshTokenOptional.get());
            UserRegister user = validRefreshToken.getUserRegister();
            List<String> role = new ArrayList<>();
            role.add(user.getRole());

            // Generate a new access token
            String accessToken = jwtService.generateToken(user.getEmailId(), role);

            response.put("accessToken", accessToken);
            response.put("role", role.get(0));

            return response;
        }
        throw new RuntimeException("Invalid refresh token");
    }



    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendResetPasswordOTP(String email) {

        Optional<UserRegister> userRegisterOptional=userRepo.findByEmailId(email);


        if (userRegisterOptional.isPresent()) {
            String otp = generateOTP();
            otpMap.put(email, otp); // Store OTP temporarily
            emailService.sendResetPasswordOTP(email, otp);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public boolean verifyOTP(String email, String otp) {
        String storedOTP = otpMap.get(email);
        return storedOTP != null && storedOTP.equals(otp);
    }

    public void resetPassword(String email, String newPassword) {

        Optional<UserRegister> userRegisterOptional=userRepo.findByEmailId(email);

        if (userRegisterOptional.isPresent()) {

            UserRegister user=userRegisterOptional.get();

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);

            userRepo.save(user);

        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

