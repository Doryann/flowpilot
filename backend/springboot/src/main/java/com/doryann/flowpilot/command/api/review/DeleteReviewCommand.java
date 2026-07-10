package com.doryann.flowpilot.command.api.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Builder
@Value
public class DeleteReviewCommand {
    @NotNull
    @TargetAggregateIdentifier
    UUID reviewId;
}
