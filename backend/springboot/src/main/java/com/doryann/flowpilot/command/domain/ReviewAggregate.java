package com.doryann.flowpilot.command.domain;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.doryann.flowpilot.command.api.review.CreateReviewCommand;
import com.doryann.flowpilot.command.api.review.DeleteReviewCommand;
import com.doryann.flowpilot.command.api.review.UpdateReviewCommand;
import com.doryann.flowpilot.event.review.ReviewCreatedEvent;
import com.doryann.flowpilot.event.review.ReviewDeletedEvent;
import com.doryann.flowpilot.event.review.ReviewUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ReviewAggregate {

    @AggregateIdentifier
    private UUID reviewId;

    private UUID mediaId;
    private UUID userId;

    private Integer rating;
    private String content;

    private boolean deleted;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    protected ReviewAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public ReviewAggregate(CreateReviewCommand command) {
        apply(
                ReviewCreatedEvent.builder()
                        .reviewId(command.getReviewId())
                        .mediaId(command.getMediaId())
                        .userId(command.getUserId())
                        .rating(command.getRating())
                        .content(command.getContent())
                        .createdAt(now())
                        .build()
        );
    }

    @CommandHandler
    public void handle(UpdateReviewCommand command) {
        assertNotDeleted();

        apply(
                ReviewUpdatedEvent.builder()
                        .reviewId(command.getReviewId())
                        .mediaId(this.mediaId)
                        .userId(this.userId)
                        .rating(command.getRating())
                        .content(command.getContent())
                        .updatedAt(now())
                        .build()
        );
    }

    @CommandHandler
    public void handle(DeleteReviewCommand command) {
        assertNotDeleted();

        apply(
                ReviewDeletedEvent.builder()
                        .reviewId(command.getReviewId())
                        .mediaId(this.mediaId)
                        .userId(this.userId)
                        .deletedAt(now())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(ReviewCreatedEvent event) {
        this.reviewId = event.getReviewId();
        this.mediaId = event.getMediaId();
        this.userId = event.getUserId();
        this.rating = event.getRating();
        this.content = event.getContent();
        this.createdAt = event.getCreatedAt();
        this.deleted = false;
    }

    @EventSourcingHandler
    public void on(ReviewUpdatedEvent event) {
        this.rating = event.getRating();
        this.content = event.getContent();
        this.updatedAt = event.getUpdatedAt();
    }

    @EventSourcingHandler
    public void on(ReviewDeletedEvent event) {
        this.deleted = true;
        this.updatedAt = event.getDeletedAt();
    }

    private void assertNotDeleted() {
        if (deleted) {
            throw new IllegalStateException("Review is deleted");
        }
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}