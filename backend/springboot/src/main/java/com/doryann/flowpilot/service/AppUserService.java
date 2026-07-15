package com.doryann.flowpilot.service;

import com.doryann.flowpilot.api.model.UpdateUserProfileRequest;
import com.doryann.flowpilot.api.model.UserDTO;
import com.doryann.flowpilot.entity.AppUser;
import com.doryann.flowpilot.mapper.UserMapper;
import com.doryann.flowpilot.repository.AppUserRepository;
import com.doryann.flowpilot.shared.AuthProvider;
import com.doryann.flowpilot.shared.UserRole;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class AppUserService {

    private final AppUserRepository userRepository;
    private final UserMapper userMapper;

    public AppUser createOrUpdateOAuthUser(
            AuthProvider provider,
            String providerSubject,
            String email,
            String displayName,
            String avatarUrl
    ) {
        String normalizedEmail = normalizeEmail(email);
        String normalizedDisplayName = normalizeDisplayName(displayName);

        return userRepository.findByProviderAndProviderSubject(provider, providerSubject)
                .map(existingUser -> updateOAuthUser(
                        existingUser,
                        normalizedEmail,
                        normalizedDisplayName,
                        avatarUrl
                ))
                .orElseGet(() -> createOAuthUser(
                        provider,
                        providerSubject,
                        normalizedEmail,
                        normalizedDisplayName,
                        avatarUrl
                ));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(UUID userId) {
        return userRepository.findByIdAndActiveTrue(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found for userId=" + userId
                ));
    }

    public UUID updateProfile(UUID userId, @Valid UpdateUserProfileRequest updateUserProfileRequest) {
        AppUser existingUser = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found for userId=" + userId
                ));

        String username = normalizeUsername(updateUserProfileRequest.getUsername());

        if (userRepository.existsByUsernameAndIdNot(username, userId)) {
            throw new IllegalStateException("Username already exists: " + username);
        }

        existingUser.setUsername(username);
        existingUser.setDisplayName(updateUserProfileRequest.getDisplayName());
        existingUser.setAvatarUrl(updateUserProfileRequest.getAvatarUrl());
        existingUser.setUpdatedAt(now());

        userRepository.save(existingUser);

        return existingUser.getId();
    }

    public void deactivateUser(UUID userId) {
        AppUser existingUser = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found for userId=" + userId
                ));

        existingUser.setActive(false);
        existingUser.setUpdatedAt(now());

        userRepository.save(existingUser);
    }

    private AppUser updateOAuthUser(
            AppUser existingUser,
            String email,
            String displayName,
            String avatarUrl
    ) {
        existingUser.setEmail(email);
        existingUser.setDisplayName(displayName);
        existingUser.setAvatarUrl(avatarUrl);
        existingUser.setUpdatedAt(now());

        return userRepository.save(existingUser);
    }

    private AppUser createOAuthUser(
            AuthProvider provider,
            String providerSubject,
            String email,
            String displayName,
            String avatarUrl
    ) {
        AppUser newUser = new AppUser();

        newUser.setId(UUID.randomUUID());
        newUser.setProvider(provider);
        newUser.setProviderSubject(providerSubject);
        newUser.setEmail(email);
        newUser.setUsername(generateUsername(email, displayName));
        newUser.setDisplayName(displayName);
        newUser.setAvatarUrl(avatarUrl);
        newUser.setRole(UserRole.USER);
        newUser.setActive(true);
        newUser.setCreatedAt(now());
        newUser.setUpdatedAt(null);

        return userRepository.save(newUser);
    }

    private String generateUsername(String email, String displayName) {
        String baseUsername;

        if (email != null && email.contains("@")) {
            baseUsername = email.substring(0, email.indexOf("@"));
        } else {
            baseUsername = displayName;
        }

        baseUsername = normalizeUsername(baseUsername)
                .toLowerCase()
                .replaceAll("[^a-z0-9._-]", "");

        if (baseUsername.isBlank()) {
            baseUsername = "user";
        }

        String username = baseUsername;
        int suffix = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + suffix;
            suffix++;
        }

        return username;
    }

    private String normalizeUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username is required");
        }

        return username.trim();
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        return email.trim().toLowerCase();
    }

    private String normalizeDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            return "User";
        }

        return displayName.trim();
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}