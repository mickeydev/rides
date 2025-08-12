package com.communityrideshare.auth.web.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
