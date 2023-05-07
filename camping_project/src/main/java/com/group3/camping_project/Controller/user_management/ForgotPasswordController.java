package com.group3.camping_project.controller.user_management;

import com.group3.camping_project.controller.user_management.request.ForgotPasswordRequest;
import com.group3.camping_project.controller.user_management.request.ResetPasswordRequest;
import com.group3.camping_project.entities.PasswordResetToken;
import com.group3.camping_project.entities.User;
import com.group3.camping_project.repository.IUserRepo;
import com.group3.camping_project.service.user_management.EmailService;
import com.group3.camping_project.service.user_management.IUserService;
import com.group3.camping_project.service.user_management.PasswordResetTokenService;
import com.group3.camping_project.service.user_management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ForgotPasswordController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private final EmailService emailService;

    public ForgotPasswordController(UserService userService,
                                    PasswordResetTokenService passwordResetTokenService,
                                    EmailService emailService) {
        this.iUserService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        User user = iUserService.findByEmail(email).get();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        PasswordResetToken token = passwordResetTokenService.generateTokenForUser(user);
        emailService.sendPasswordResetEmail(email, token);

        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String tokenString = request.getToken();
        PasswordResetToken token = passwordResetTokenService.findByToken(tokenString);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expired token");
        }

        Date expiryDate = token.getExpiryDate();
        if (expiryDate.before(new Date())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expired token");
        }

        User user = token.getUser();
        String newPassword = request.getNewPassword();
        user.setPassword(newPassword);
        iUserService.createUser(user);

        passwordResetTokenService.delete(token);

        return ResponseEntity.ok("Password reset successful");
    }
}
