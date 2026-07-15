package com.doryann.flowpilot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review_view")
public class ReviewView {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "media_id", nullable = false)
    private UUID mediaId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "content", length = 1024)
    private String content;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
