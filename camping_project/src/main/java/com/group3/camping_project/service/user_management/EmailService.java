package com.group3.camping_project.service.user_management;

import com.group3.camping_project.entities.PasswordResetToken;

public interface EmailService {
    void sendPasswordResetEmail(String email, PasswordResetToken token);

    //void sendPasswordResetEmail(String email, PasswordResetToken token);
}
