package com.doryann.flowpilot.controller;

import com.doryann.flowpilot.api.UserApi;
import com.doryann.flowpilot.api.model.CreateUserBody;
import com.doryann.flowpilot.api.model.IdentificationResponse;
import com.doryann.flowpilot.api.model.UserDTO;
import com.doryann.flowpilot.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final AppUserService appUserService;

    @Override
    public ResponseEntity<UserDTO> findUserById(UUID userId) {
        log.info("Find user by id={}", userId);

        UserDTO user = appUserService.findById(userId);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<IdentificationResponse> createUser(@Valid CreateUserBody createUserBody) {
        log.info("Create user {}", createUserBody.getUsername());

        UUID userId = appUserService.createUser(createUserBody);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdentificationResponse().id(userId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> updateUser(UUID userId, @Valid UserDTO user) {
        log.info("Update user {}", user.getUsername());

        appUserService.updateUser(userId, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdentificationResponse().id(userId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> deleteUser(UUID userId) {
        log.info("Delete user userId={}", userId);

        appUserService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(new IdentificationResponse().id(userId));
    }
}
