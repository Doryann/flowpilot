package com.doryann.flowpilot.command.api.media;

import com.doryann.flowpilot.api.model.MediaStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Value
public class ChangeMediaStatusCommand {
    @NotNull
    @TargetAggregateIdentifier
    UUID mediaId;

    @NotNull
    MediaStatus status;
}
