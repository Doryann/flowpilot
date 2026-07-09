package com.doryann.flowpilot.command.api.media;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Builder
@Value
public class DeleteMediaCommand {
    @NotNull
    @TargetAggregateIdentifier
    UUID mediaId;
}
