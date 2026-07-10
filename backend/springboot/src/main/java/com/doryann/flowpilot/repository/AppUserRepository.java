package com.doryann.flowpilot.repository;

import com.doryann.flowpilot.entity.AppUser;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByIdAndActiveTrue(UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String username, UUID id);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
