package com.doryann.flowpilot.controller;

import com.doryann.flowpilot.api.UserApi;
import com.doryann.flowpilot.api.model.IdentificationResponse;
import com.doryann.flowpilot.api.model.UpdateUserProfileRequest;
import com.doryann.flowpilot.api.model.UserDTO;
import com.doryann.flowpilot.service.CurrentUserService;
import com.doryann.flowpilot.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final AppUserService appUserService;
    private final CurrentUserService currentUserService;

    @Override
    public ResponseEntity<UserDTO> findCurrentUser() {
        UUID currentUserId = currentUserService.getCurrentUserId();

        log.info("Find current user userId={}", currentUserId);

        UserDTO user = appUserService.findById(currentUserId);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<IdentificationResponse> updateCurrentUser(@Valid UpdateUserProfileRequest updateUserProfileRequest) {
        UUID currentUserId = currentUserService.getCurrentUserId();

        log.info("Update current user userId={}", currentUserId);

        UUID updatedUserId = appUserService.updateProfile(currentUserId, updateUserProfileRequest);

        return ResponseEntity.ok(new IdentificationResponse().id(updatedUserId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> deleteCurrentUser() {
        UUID currentUserId = currentUserService.getCurrentUserId();

        log.info("Deactivate current user userId={}", currentUserId);

        appUserService.deactivateUser(currentUserId);

        return ResponseEntity.ok(new IdentificationResponse().id(currentUserId));
    }
}