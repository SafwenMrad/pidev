package com.group3.camping_project.service.user_management;

import com.group3.camping_project.entities.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

    private JavaMailSender mailSender;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService ;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendPasswordResetEmail(String email, PasswordResetToken token) {
        MimeMessage message = mailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("camping-project@picloud.com");
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText("Please use the following link to reset your password: " +
                    "http://localhost:8090/reset-password?token=" + token.getToken() , true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}
