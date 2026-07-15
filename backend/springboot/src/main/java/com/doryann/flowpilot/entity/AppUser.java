package com.doryann.flowpilot.entity;

import com.doryann.flowpilot.shared.AuthProvider;
import com.doryann.flowpilot.shared.UserRole;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "display_name", nullable = false, length = 150)
    private String displayName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    @Column(name = "provider", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_subject", nullable = false, length = 255)
    private String providerSubject;

    @Column(name = "role", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}