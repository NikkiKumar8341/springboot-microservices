package com.example.security_basic.controller;


import com.example.security_basic.dto.AuthRequest;
import com.example.security_basic.entity.Student;
import com.example.security_basic.entity.UserRegister;
import com.example.security_basic.service.JwtService;
import com.example.security_basic.service.RefreshTokenService;
import com.example.security_basic.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserRegisterController {


    @Autowired
    private RegisterService registerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome ";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserRegister userRegister){

        return registerService.addUser(userRegister);
    }




    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        try {
            Map<String, Object> response = registerService.login(authRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            Map<String, Object> response = registerService.refreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    @PostMapping("/reset-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email){
        try{
            registerService.sendResetPasswordOTP(email);
            return  ResponseEntity.ok("Reset password OTP sent to your email");
        }catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body("User not found");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while sending OTP");
        }
    }


    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword){
        try {
            if( registerService.verifyOTP(email,otp)){
                registerService.resetPassword(email,newPassword);

                return ResponseEntity.ok("Password reset successfully");
            }else {
                return ResponseEntity.badRequest().body("Invaild OTP");
            }
        }catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while resetting password");
        }
    }

}
