package com.doryann.flowpilot.event.media;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class RateMediaEvent {
    @NotNull
    UUID mediaId;
    int rating;
}
