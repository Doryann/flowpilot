package com.doryann.flowpilot.command.api.media;

import com.doryann.flowpilot.api.model.MediaStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Builder
@Value
public class ChangeMediaStatusCommand {
    @NotNull
    @TargetAggregateIdentifier
    UUID mediaId;
    MediaStatus status;
}
