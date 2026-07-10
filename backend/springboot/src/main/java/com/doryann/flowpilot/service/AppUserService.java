package com.doryann.flowpilot.service;

import com.doryann.flowpilot.api.model.CreateUserBody;
import com.doryann.flowpilot.api.model.UserDTO;
import com.doryann.flowpilot.entity.AppUser;
import com.doryann.flowpilot.mapper.UserMapper;
import com.doryann.flowpilot.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class AppUserService {

    private final AppUserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional(readOnly = true)
    public UserDTO findById(UUID userId) {
        return userRepository.findByIdAndActiveTrue(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found for userId=" + userId
                ));
    }

    public UUID createUser(@Valid CreateUserBody createUserBody) {
        String username = normalizeUsername(createUserBody.getUsername());
        String email = normalizeEmail(createUserBody.getEmail());

        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username already exists: " + username);
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already exists: " + email);
        }

        AppUser user = userMapper.toEntity(createUserBody);

        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(null);

        AppUser savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public UUID updateUser(UUID userId, @Valid UserDTO userDTO) {
        AppUser existingUser = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found for userId=" + userId
                ));

        String username = normalizeUsername(userDTO.getUsername());
        String email = normalizeEmail(userDTO.getEmail());

        if (userRepository.existsByUsernameAndIdNot(username, userId)) {
            throw new IllegalStateException("Username already exists: " + username);
        }

        if (userRepository.existsByEmailAndIdNot(email, userId)) {
            throw new IllegalStateException("Email already exists: " + email);
        }

        userMapper.updateEntityFromDto(userDTO, existingUser);

        existingUser.setUsername(username);
        existingUser.setEmail(email);
        existingUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(existingUser);

        return existingUser.getId();
    }

    public void deleteUser(UUID userId) {
        AppUser existingUser = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found for userId=" + userId
                ));

        existingUser.setActive(false);
        existingUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(existingUser);
    }

    private String normalizeUsername(String username) {
        return username.trim();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}