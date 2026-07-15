package com.doryann.flowpilot.entity;

import com.doryann.flowpilot.api.model.MediaStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "media_view")
public class MediaView {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotNull
    @Column(name = "media_type", nullable = false, length = 50)
    private String mediaType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private MediaStatus status;

    @Column(name = "platform", length = 100)
    private String platform;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "average_user_rating", precision = 3, scale = 1)
    private BigDecimal averageUserRating;

    @Builder.Default
    @Column(name = "user_rating_count", nullable = false)
    private int userRatingCount = 0;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}