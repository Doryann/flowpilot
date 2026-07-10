package com.doryann.flowpilot.event.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class ReviewUpdatedEvent {
    @NotNull
    UUID reviewId;
    @NotNull
    UUID mediaId;
    @NotNull
    UUID userId;
    int rating;
    String content;
}
