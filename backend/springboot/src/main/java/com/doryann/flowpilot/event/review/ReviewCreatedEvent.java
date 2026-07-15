package com.doryann.flowpilot.event.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;


@Builder
@Value
public class ReviewCreatedEvent {

    @NotNull
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

    @NotNull
    OffsetDateTime createdAt;
}
