package com.doryann.flowpilot.query.projection;

import com.doryann.flowpilot.entity.ReviewView;
import com.doryann.flowpilot.event.review.ReviewCreatedEvent;
import com.doryann.flowpilot.mapper.ReviewMapper;
import com.doryann.flowpilot.repository.MediaViewRepository;
import com.doryann.flowpilot.repository.ReviewViewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewProjectionTest {

    @Mock
    private ReviewViewRepository reviewViewRepository;

    @Mock
    private MediaViewRepository mediaViewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewProjection reviewProjection;

    @Test
    void shouldSaveReviewAndRefreshMediaStatsWhenReviewCreated() {
        UUID mediaId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
                .reviewId(reviewId)
                .mediaId(mediaId)
                .userId(userId)
                .rating(9)
                .content("Excellent")
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        ReviewView view = ReviewView.builder()
                .id(reviewId)
                .mediaId(mediaId)
                .userId(userId)
                .rating(9)
                .content("Excellent")
                .createdAt(event.getCreatedAt())
                .build();

        when(reviewMapper.toView(event)).thenReturn(view);
        when(mediaViewRepository.refreshRatingStats(mediaId)).thenReturn(1);

        reviewProjection.on(event);

        verify(reviewViewRepository).save(view);
        verify(mediaViewRepository).refreshRatingStats(mediaId);
    }
}