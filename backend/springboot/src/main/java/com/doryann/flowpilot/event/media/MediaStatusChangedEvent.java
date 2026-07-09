package com.doryann.flowpilot.event.media;

import com.doryann.flowpilot.api.model.MediaStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class MediaStatusChangedEvent {
    @NotNull
    UUID mediaId;
    MediaStatus status;
}
