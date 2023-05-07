package com.group3.camping_project.repository;

import com.group3.camping_project.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPasswordResetTokenRepo extends JpaRepository<PasswordResetToken,Long> {
    public PasswordResetToken findByToken(String tokenString);
}
