package com.doryann.flowpilot.controller;

import com.doryann.flowpilot.api.MediaCommandApi;
import com.doryann.flowpilot.api.model.*;
import com.doryann.flowpilot.command.api.media.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MediaCommandController implements MediaCommandApi {

    private final CommandGateway commandGateway;

    @Override
    public ResponseEntity<Void> changeMediaStatus(
            UUID mediaId,
            @Valid ChangeMediaStatusRequest changeMediaStatusRequest
    ) {
        log.info("Changing media status for mediaId={}", mediaId);

        commandGateway.sendAndWait(ChangeMediaStatusCommand.builder().mediaId(mediaId).status(changeMediaStatusRequest.getStatus()).build());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<IdentificationResponse> createMedia(@Valid CreateMediaRequest createMediaRequest) {
        log.info("Create media {}", createMediaRequest.getTitle());
        UUID mediaId = UUID.randomUUID();
        commandGateway.sendAndWait(
                CreateMediaCommand.builder()
                        .mediaId(mediaId)
                        .title(createMediaRequest.getTitle())
                        .status(createMediaRequest.getStatus())
                        .type(createMediaRequest.getType())
                        .platform(createMediaRequest.getPlatform())
                        .releaseYear(createMediaRequest.getReleaseYear())
                        .description(createMediaRequest.getDescription())
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdentificationResponse().id(mediaId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> updateMedia(UUID mediaId, @Valid CreateMediaRequest updateMediaRequest) {
        log.info("Update media {}", updateMediaRequest.getTitle());
        commandGateway.sendAndWait(
                UpdateMediaCommand.builder()
                        .mediaId(mediaId)
                        .title(updateMediaRequest.getTitle())
                        .status(updateMediaRequest.getStatus())
                        .type(updateMediaRequest.getType())
                        .platform(updateMediaRequest.getPlatform())
                        .releaseYear(updateMediaRequest.getReleaseYear())
                        .description(updateMediaRequest.getDescription())
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdentificationResponse().id(mediaId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> deleteMedia(UUID mediaId) {
        log.info("Delete media mediaId={}", mediaId);

        commandGateway.sendAndWait(
                DeleteMediaCommand.builder()
                        .mediaId(mediaId)
                        .build()
        );

        return ResponseEntity.status(HttpStatus.OK).body(new IdentificationResponse().id(mediaId));
    }
}