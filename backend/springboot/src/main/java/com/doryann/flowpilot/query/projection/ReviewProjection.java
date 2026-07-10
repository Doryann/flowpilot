package com.doryann.flowpilot.query.projection;

import com.doryann.flowpilot.event.review.ReviewCreatedEvent;
import com.doryann.flowpilot.event.review.ReviewUpdatedEvent;
import com.doryann.flowpilot.mapper.ReviewMapper;
import com.doryann.flowpilot.repository.MediaViewRepository;
import com.doryann.flowpilot.repository.ReviewViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
@ProcessingGroup("review-projections")
public class ReviewProjection {

    private final MediaViewRepository mediaViewRepository;
    private final ReviewViewRepository reviewViewRepository;
    private final ReviewMapper reviewMapper;

    @EventHandler
    public void on(ReviewUpdatedEvent event) {
        log.info("Projecting ReviewUpdatedEvent for mediaId={} userId={}",
                event.getMediaId(),
                event.getUserId());

        reviewViewRepository.save(reviewMapper.toView(event));

        refreshMediaRatingStats(event.getMediaId());
    }

    @EventHandler
    public void on(ReviewCreatedEvent event) {
        log.info("Projecting ReviewCreatedEvent for mediaId={} userId={}",
                event.getMediaId(),
                event.getUserId());

        reviewViewRepository.save(reviewMapper.toView(event));

        refreshMediaRatingStats(event.getMediaId());
    }

    private void refreshMediaRatingStats(UUID mediaId) {
        int updatedRows = mediaViewRepository.refreshRatingStats(mediaId);

        if (updatedRows == 0) {
            throw new IllegalStateException("MediaView not found for mediaId=" + mediaId);
        }
    }
}
