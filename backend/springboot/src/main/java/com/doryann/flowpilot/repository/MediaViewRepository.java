package com.doryann.flowpilot.repository;

import com.doryann.flowpilot.entity.MediaView;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MediaViewRepository extends JpaRepository<MediaView, UUID> {
    @Modifying
    @Query("update MediaView m set m.status = :status where m.id = :mediaId")
    int updateStatusById(@NotNull UUID mediaId, String status);

    @Modifying
    @Query(value = """
    update media_view m
    set average_user_rating = (
            select round(avg(rv.rating), 1)
            from review_view rv
            where rv.media_id = :mediaId
        ),
        user_rating_count = (
            select count(*)::int
            from review_view rv
            where rv.media_id = :mediaId
        )
    where m.id = :mediaId
""", nativeQuery = true)
    int refreshRatingStats(@Param("mediaId") UUID mediaId);
}
