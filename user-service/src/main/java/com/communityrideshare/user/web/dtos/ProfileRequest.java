package com.communityrideshare.user.web.dtos;

import com.communityrideshare.user.domain.Profile;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileRequest {
    @NotBlank
    private String fullName;
    private String phone;
    private String profilePhotoUrl;
    private Profile.Role role;
}
