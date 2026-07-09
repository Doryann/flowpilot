package com.doryann.flowpilot.event.media;

import com.doryann.flowpilot.api.model.MediaStatus;
import com.doryann.flowpilot.api.model.MediaType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class MediaUpdatedEvent {
    @NotNull
    UUID mediaId;
    String title;
    MediaStatus status;
    MediaType type;
    String platform;
    Integer releaseYear;
    String description;
}
