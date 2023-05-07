package com.group3.camping_project.controller.user_management;


import com.group3.camping_project.controller.user_management.request.LoginRequest;
import com.group3.camping_project.controller.user_management.request.SignupRequest;
import com.group3.camping_project.controller.user_management.response.JwtResponse;
import com.group3.camping_project.controller.user_management.response.MessageResponse;
import com.group3.camping_project.entities.User;

import com.group3.camping_project.repository.IRoleRepo;
import com.group3.camping_project.repository.IUserRepo;

import com.group3.camping_project.security.JwtUtils;
import com.group3.camping_project.service.user_management.* ;
import com.group3.camping_project.service.user_management.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepo userRepository;

  @Autowired
   IRoleRepo roleRepository;

    @Autowired
    private AuthService authService ;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));

        if (!user.isEmailVerified()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please verify your email first!"));
        }


        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody  SignupRequest signUpRequest /* @RequestParam(required = false) MultipartFile file*/) {
        authService.registerUser(signUpRequest/*,file*/);
        return ResponseEntity.ok(
                new MessageResponse(
                        "A verification email has been sent to your email address. Please verify your email before logging in.")
        );
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        authService.validateEmailVerificationToken(token);
        return ResponseEntity.ok(new MessageResponse("Email verified successfully!"));
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }











//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(
//                signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                signUpRequest.getFirstName(),
//                signUpRequest.getLastName(),
//                signUpRequest.getGender(),
//                signUpRequest.getPhoneNumber(),
//                encoder.encode(signUpRequest.getPassword()));
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
}
