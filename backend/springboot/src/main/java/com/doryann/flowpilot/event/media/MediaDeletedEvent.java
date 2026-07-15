package com.doryann.flowpilot.event.media;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Value
public class MediaDeletedEvent {
    @NotNull
    UUID mediaId;

    @NotNull
    OffsetDateTime deletedAt;
}
