package com.doryann.flowpilot.controller;

import com.doryann.flowpilot.api.ReviewCommandApi;
import com.doryann.flowpilot.api.model.CreateReviewRequest;
import com.doryann.flowpilot.api.model.IdentificationResponse;
import com.doryann.flowpilot.command.api.review.CreateReviewCommand;
import com.doryann.flowpilot.command.api.review.DeleteReviewCommand;
import com.doryann.flowpilot.command.api.review.UpdateReviewCommand;
import com.doryann.flowpilot.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewCommandController implements ReviewCommandApi {
    private final CommandGateway commandGateway;
    private final ReviewService reviewService;

    @Override
    public ResponseEntity<IdentificationResponse> createReview(@Valid CreateReviewRequest createReviewRequest) {
        assert createReviewRequest.getMedia() != null;
        if (reviewService.existsByMediaIdAndUserId(createReviewRequest.getMedia().getId(), UUID.randomUUID())) {
            throw new IllegalStateException("User has already reviewed this media");
        }
        log.info("Create review for media {}", createReviewRequest.getMedia().getTitle());
        UUID reviewId = UUID.randomUUID();
        commandGateway.sendAndWait(
                CreateReviewCommand.builder()
                        .reviewId(reviewId)
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdentificationResponse().id(reviewId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> updateReview(UUID reviewId, @Valid CreateReviewRequest updateReviewRequest) {
        assert updateReviewRequest.getMedia() != null;
        log.info("Update review for media {}", updateReviewRequest.getMedia().getTitle());
        commandGateway.sendAndWait(
                UpdateReviewCommand.builder()
                        .reviewId(reviewId)
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdentificationResponse().id(reviewId));
    }

    @Override
    public ResponseEntity<IdentificationResponse> deleteReview(UUID reviewId) {
        log.info("Delete review reviewId={}", reviewId);

        commandGateway.sendAndWait(
                DeleteReviewCommand.builder()
                        .reviewId(reviewId)
                        .build()
        );

        return ResponseEntity.status(HttpStatus.OK).body(new IdentificationResponse().id(reviewId));
    }
}
