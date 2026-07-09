package com.doryann.flowpilot.command.api.media;

import com.doryann.flowpilot.api.model.MediaStatus;
import com.doryann.flowpilot.api.model.MediaType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Builder
@Value
public class UpdateMediaCommand {
    @NotNull
    @TargetAggregateIdentifier
    UUID mediaId;
    String title;
    MediaStatus status;
    MediaType type;
    String platform;
    Integer releaseYear;
    String description;
}
