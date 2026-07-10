package com.doryann.flowpilot.query.api.review;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class FindReviewByIdQuery {
    @NotNull
    UUID reviewId;
}
