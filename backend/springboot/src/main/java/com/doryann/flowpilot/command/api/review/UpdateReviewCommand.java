package com.doryann.flowpilot.command.api.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.lang.annotation.Native;
import java.util.UUID;

@Builder
@Value
public class UpdateReviewCommand {
    @NotNull
    @TargetAggregateIdentifier
    UUID reviewId;
    @NotNull
    UUID mediaId;
    @NotNull
    UUID userId;
    int rating;
    String content;
}
