package com.group3.camping_project.service.user_management;

import com.group3.camping_project.entities.PasswordResetToken;
import com.group3.camping_project.entities.User;

public interface PasswordResetTokenService {
    PasswordResetToken generateTokenForUser(User user);

    PasswordResetToken findByToken(String tokenString);

    void delete(PasswordResetToken token);

//    void sendPasswordResetEmail(String email, String token);
}
