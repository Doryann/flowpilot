package com.doryann.flowpilot.command.domain;

import com.doryann.flowpilot.api.model.MediaStatus;
import com.doryann.flowpilot.api.model.MediaType;
import com.doryann.flowpilot.command.api.media.*;
import com.doryann.flowpilot.event.media.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class MediaAggregate {

    @AggregateIdentifier
    private UUID mediaId;
    String title;
    MediaType type;
    MediaStatus status;
    String platform;
    int releaseYear;
    String description;
    private boolean deleted;

    /** Instantiates a new Media */
    protected MediaAggregate() {}

    /** Create new Media */
    @CommandHandler
    public MediaAggregate(CreateMediaCommand command) {
        AggregateLifecycle.apply(
                MediaCreatedEvent.builder()
                        .mediaId(command.getMediaId())
                        .title(command.getTitle())
                        .status(command.getStatus())
                        .type(command.getType())
                        .platform(command.getPlatform())
                        .releaseYear(command.getReleaseYear())
                        .description(command.getDescription())
                        .build());
    }

    @EventSourcingHandler
    public void on(MediaCreatedEvent event) {
        this.mediaId = event.getMediaId();
        this.title = event.getTitle();
        this.status = event.getStatus();
        this.type = event.getType();
        this.platform = event.getPlatform();
        this.releaseYear = event.getReleaseYear();
        this.description = event.getDescription();
    }

    /** Update media */
    @CommandHandler
    public MediaAggregate(UpdateMediaCommand command) {
        AggregateLifecycle.apply(
                MediaCreatedEvent.builder()
                        .mediaId(command.getMediaId())
                        .title(command.getTitle())
                        .status(command.getStatus())
                        .type(command.getType())
                        .platform(command.getPlatform())
                        .releaseYear(command.getReleaseYear())
                        .description(command.getDescription())
                        .build());
    }

    @EventSourcingHandler
    public void on(MediaUpdatedEvent event) {
        this.title = event.getTitle();
        this.status = event.getStatus();
        this.type = event.getType();
        this.platform = event.getPlatform();
        this.releaseYear = event.getReleaseYear();
        this.description = event.getDescription();
    }

    /** Update media status */
    @CommandHandler
    public void handle(ChangeMediaStatusCommand command) {
        AggregateLifecycle.apply(MediaStatusChangedEvent.builder().mediaId(command.getMediaId()).status(command.getStatus()).build());
    }

    @EventSourcingHandler
    public void on(MediaStatusChangedEvent event) {
        this.status = event.getStatus();
    }

    /** Delete media */
    @CommandHandler
    public void handle(DeleteMediaCommand command) {
        if (deleted) {
            throw new IllegalStateException("Media is already deleted");
        }

        AggregateLifecycle.apply(
                MediaDeletedEvent.builder()
                        .mediaId(command.getMediaId())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(MediaDeletedEvent event) {
        this.deleted = true;
    }
}