package com.doryann.flowpilot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaView {
    @Id
    private UUID id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 255)
    private String mediaType;

    @NotNull
    @Size(max = 50)
    private String status;

    @Size(max = 100)
    private String platform;

    @Size(max = 2048)
    private String description;

    private int releaseYear;
    private int averageUserRating;
    private int userRatingCount;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
