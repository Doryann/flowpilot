package com.doryann.flowpilot.event.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Value
public class ReviewDeletedEvent {

    @NotNull
    UUID reviewId;

    @NotNull
    UUID mediaId;

    @NotNull
    UUID userId;

    @NotNull
    OffsetDateTime deletedAt;
}