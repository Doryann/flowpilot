package com.doryann.flowpilot.repository;

import com.doryann.flowpilot.entity.MediaView;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MediaViewRepository extends JpaRepository<MediaView, UUID> {
    @Modifying
    @Query("update MediaView m set m.status = :status where m.id = :mediaId")
    int updateStatusById(@NotNull UUID mediaId, String status);

    @Modifying
    @Query("update MediaView m set m.rating = :rating where m.id = :mediaId")
    int updateRatingById(@NotNull UUID mediaId, int rating);
}
