package com.doryann.flowpilot.command.domain;

import com.doryann.flowpilot.command.api.review.CreateReviewCommand;
import com.doryann.flowpilot.command.api.review.UpdateReviewCommand;
import com.doryann.flowpilot.event.review.ReviewCreatedEvent;
import com.doryann.flowpilot.event.review.ReviewDeletedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;
import static org.axonframework.test.matchers.Matchers.payloadsMatching;

class ReviewAggregateTest {

    private FixtureConfiguration<ReviewAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(ReviewAggregate.class);
    }

    @Test
    void shouldCreateReview() {
        UUID reviewId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        CreateReviewCommand command = CreateReviewCommand.builder()
                .reviewId(reviewId)
                .mediaId(mediaId)
                .userId(userId)
                .rating(9)
                .content("Excellent jeu.")
                .build();

        fixture.givenNoPriorActivity()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(payload -> {
                            if (!(payload instanceof ReviewCreatedEvent event)) {
                                return false;
                            }

                            return event.getReviewId().equals(reviewId)
                                    && event.getMediaId().equals(mediaId)
                                    && event.getUserId().equals(userId)
                                    && event.getRating().equals(9)
                                    && event.getContent().equals("Excellent jeu.")
                                    && event.getCreatedAt() != null;
                        }),
                        andNoMore()
                )));
    }

    @Test
    void shouldRejectUpdateWhenReviewIsDeleted() {
        UUID reviewId = UUID.randomUUID();
        UUID mediaId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ReviewCreatedEvent createdEvent = ReviewCreatedEvent.builder()
                .reviewId(reviewId)
                .mediaId(mediaId)
                .userId(userId)
                .rating(9)
                .content("Excellent jeu.")
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        ReviewDeletedEvent deletedEvent = ReviewDeletedEvent.builder()
                .reviewId(reviewId)
                .mediaId(mediaId)
                .userId(userId)
                .deletedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        UpdateReviewCommand command = UpdateReviewCommand.builder()
                .reviewId(reviewId)
                .rating(8)
                .content("Je baisse un peu la note.")
                .build();

        fixture.given(createdEvent, deletedEvent)
                .when(command)
                .expectException(IllegalStateException.class);
    }
}