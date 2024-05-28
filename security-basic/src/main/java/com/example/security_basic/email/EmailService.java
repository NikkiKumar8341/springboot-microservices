package com.example.security_basic.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    public void sendRegistrationEmail(String recipientEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Registration Confirmation");
        mailMessage.setText("Dear user, \n\nYou have successfully registered to our platform. Thank you!");

        javaMailSender.send(mailMessage);
    }

    public void sendResetPasswordOTP(String recipientEmail, String otp) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Password Reset OTP");
        mailMessage.setText("Your OTP to reset the password is: " + otp);

        javaMailSender.send(mailMessage);
    }
}
