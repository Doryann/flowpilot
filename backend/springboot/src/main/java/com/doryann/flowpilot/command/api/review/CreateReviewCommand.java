package com.doryann.flowpilot.command.api.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class CreateReviewCommand {
    @NotNull
    UUID reviewId;
    @NotNull
    UUID mediaId;
    @NotNull
    UUID userId;
    int rating;
    String content;
}
