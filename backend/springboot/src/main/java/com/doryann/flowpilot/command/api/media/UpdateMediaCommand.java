package com.doryann.flowpilot.command.api.media;

import com.doryann.flowpilot.api.model.MediaStatus;
import com.doryann.flowpilot.api.model.MediaType;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Size(max = 255)
    String title;

    @NotNull
    MediaStatus status;

    @NotNull
    MediaType mediaType;

    @Size(max = 100)
    String platform;

    @Min(1800)
    @Max(2100)
    Integer releaseYear;

    @Size(max = 2048)
    String description;
}
