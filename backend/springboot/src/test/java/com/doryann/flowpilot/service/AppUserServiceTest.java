package com.doryann.flowpilot.service;

import com.doryann.flowpilot.entity.AppUser;
import com.doryann.flowpilot.mapper.UserMapper;
import com.doryann.flowpilot.repository.AppUserRepository;
import com.doryann.flowpilot.shared.AuthProvider;
import com.doryann.flowpilot.shared.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    void shouldCreateOAuthUserWhenUserDoesNotExist() {
        // Given
        when(userRepository.findByProviderAndProviderSubject(
                AuthProvider.GOOGLE,
                "google-sub-123"
        )).thenReturn(Optional.empty());

        when(userRepository.existsByUsername("doryann")).thenReturn(false);

        when(userRepository.save(any(AppUser.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        AppUser createdUser = appUserService.createOrUpdateOAuthUser(
                AuthProvider.GOOGLE,
                "google-sub-123",
                "Doryann@example.com",
                "Doryann Lauro",
                "https://avatar.test/image.png"
        );

        // Then
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getProvider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(createdUser.getProviderSubject()).isEqualTo("google-sub-123");
        assertThat(createdUser.getEmail()).isEqualTo("doryann@example.com");
        assertThat(createdUser.getDisplayName()).isEqualTo("Doryann Lauro");
        assertThat(createdUser.getRole()).isEqualTo(UserRole.USER);
        assertThat(createdUser.isActive()).isTrue();
        assertThat(createdUser.getCreatedAt()).isNotNull();

        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void shouldUpdateOAuthUserWhenUserAlreadyExists() {
        // Given
        AppUser existingUser = new AppUser();
        existingUser.setId(UUID.randomUUID());
        existingUser.setProvider(AuthProvider.GOOGLE);
        existingUser.setProviderSubject("google-sub-123");
        existingUser.setEmail("old@example.com");
        existingUser.setDisplayName("Old Name");

        when(userRepository.findByProviderAndProviderSubject(
                AuthProvider.GOOGLE,
                "google-sub-123"
        )).thenReturn(Optional.of(existingUser));

        when(userRepository.save(any(AppUser.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        AppUser updatedUser = appUserService.createOrUpdateOAuthUser(
                AuthProvider.GOOGLE,
                "google-sub-123",
                "new@example.com",
                "New Name",
                "https://avatar.test/new.png"
        );

        // Then
        assertThat(updatedUser.getId()).isEqualTo(existingUser.getId());
        assertThat(updatedUser.getEmail()).isEqualTo("new@example.com");
        assertThat(updatedUser.getDisplayName()).isEqualTo("New Name");
        assertThat(updatedUser.getAvatarUrl()).isEqualTo("https://avatar.test/new.png");
        assertThat(updatedUser.getUpdatedAt()).isNotNull();

        verify(userRepository).save(existingUser);
    }
}