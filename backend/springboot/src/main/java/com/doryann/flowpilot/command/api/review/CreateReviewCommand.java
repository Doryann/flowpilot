package com.doryann.flowpilot.command.api.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Builder
@Value
public class CreateReviewCommand {

    @NotNull
    @TargetAggregateIdentifier
    UUID reviewId;

    @NotNull
    UUID mediaId;

    @NotNull
    UUID userId;

    @NotNull
    @Min(0)
    @Max(10)
    Integer rating;

    @Size(max = 2048)
    String content;
}
