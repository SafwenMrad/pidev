package com.group3.camping_project.service.user_management;


import com.group3.camping_project.controller.user_management.request.SignupRequest;
import com.group3.camping_project.entities.Image;
import com.group3.camping_project.entities.Role;
import com.group3.camping_project.entities.User;
import com.group3.camping_project.entities.enums.ERole;
import com.group3.camping_project.repository.IRoleRepo;
import com.group3.camping_project.repository.IUserRepo;
import com.group3.camping_project.repository.IVerificationTokenRepo;
import com.group3.camping_project.service.FileService.IImageService;
import com.group3.camping_project.service.user_management.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {
    @Autowired
    private IUserRepo userRepository;
    @Autowired
    private IRoleRepo roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IVerificationTokenRepo iVerificationTokenRepo ;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private IImageService iImageService ;


    public void insertRoles() {
        if (!roleRepository.existsByName(ERole.ROLE_USER)) {
            Role userRole = new Role(ERole.ROLE_USER);
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByName(ERole.ROLE_ADMIN)) {
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }

    public void registerUser(SignupRequest signUpRequest/*, MultipartFile imageFile*/) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email is already in use!");
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getGender(),
                signUpRequest.getPhoneNumber(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

//        if (imageFile != null) {
//            try {
//                Image image = iImageService.saveImage(imageFile);
//                user.setProfileImage(image);
//                userRepository.save(user);
//            } catch (IOException e) {
//                throw new RuntimeException("Error saving image: " + e.getMessage());
//            }
//        }

        //Email verif
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        iVerificationTokenRepo.save(verificationToken);

        // Send verification email to the user
        String verifyEmailLink = "http://localhost:8090/api/auth/verify-email?token=" + token;
        sendVerificationEmail(user.getEmail(), verifyEmailLink);
    }




    private void sendVerificationEmail(String toEmail, String verifyEmailLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("camping.go2023@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Verify your email address");
        message.setText("Please click the following link to verify your email address: " + verifyEmailLink);
        javaMailSender.send(message);
    }


    public void validateEmailVerificationToken(String token) {
        VerificationToken verificationToken = iVerificationTokenRepo.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid verification token."));

        User user = verificationToken.getUser();
        LocalDateTime expiryDate = verificationToken.getExpiryDate();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification token has expired. Please generate a new one.");
        }


        user.setEmailVerified(true);
        userRepository.save(user);
    }
}

