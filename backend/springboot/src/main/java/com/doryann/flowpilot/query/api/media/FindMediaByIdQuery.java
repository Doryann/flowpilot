package com.doryann.flowpilot.query.api.media;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class FindMediaByIdQuery {
    @NotNull
    UUID mediaId;
}
