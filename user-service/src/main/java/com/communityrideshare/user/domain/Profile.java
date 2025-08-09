package com.communityrideshare.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
public class Profile {

    @Id
    private UUID id; // This ID comes from the Auth service

    private String fullName;

    private String phone;

    private String profilePhotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role = Role.PASSENGER;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Role {
        DRIVER, PASSENGER, BOTH
    }
}
