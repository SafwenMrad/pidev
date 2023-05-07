package com.group3.camping_project.controller.user_management.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ResetPasswordRequest {

    private String token;
    private String newPassword;
}
