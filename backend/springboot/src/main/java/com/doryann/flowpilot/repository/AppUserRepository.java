package com.doryann.flowpilot.repository;

import com.doryann.flowpilot.entity.AppUser;
import com.doryann.flowpilot.shared.AuthProvider;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByIdAndActiveTrue(UUID id);

    Optional<AppUser> findByProviderAndProviderSubject(
            AuthProvider provider,
            String providerSubject
    );

    Optional<AppUser> findByProviderAndProviderSubjectAndActiveTrue(
            AuthProvider provider,
            String providerSubject
    );

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, UUID id);
}