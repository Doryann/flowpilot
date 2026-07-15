package com.doryann.flowpilot.service;

import com.doryann.flowpilot.entity.AppUser;
import com.doryann.flowpilot.repository.AppUserRepository;
import com.doryann.flowpilot.shared.AuthProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final AppUserRepository userRepository;

    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof OidcUser oidcUser)) {
            throw new IllegalStateException("No authenticated OIDC user found");
        }

        String registrationId = extractRegistrationId(authentication);
        AuthProvider provider = AuthProvider.fromRegistrationId(registrationId);

        return userRepository.findByProviderAndProviderSubjectAndActiveTrue(
                        provider,
                        oidcUser.getSubject()
                )
                .orElseThrow(() -> new EntityNotFoundException(
                        "Authenticated user not found in database"
                ));
    }

    private String extractRegistrationId(Authentication authentication) {
        // Pour commencer, tu peux simplifier et gérer uniquement Google.
        // Ensuite, on le rendra plus propre avec un CustomPrincipal.
        return "google";
    }
}