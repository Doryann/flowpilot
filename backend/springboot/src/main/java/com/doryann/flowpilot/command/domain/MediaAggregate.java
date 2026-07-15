package com.doryann.flowpilot.command.domain;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.doryann.flowpilot.api.model.MediaStatus;
import com.doryann.flowpilot.api.model.MediaType;
import com.doryann.flowpilot.command.api.media.ChangeMediaStatusCommand;
import com.doryann.flowpilot.command.api.media.CreateMediaCommand;
import com.doryann.flowpilot.command.api.media.DeleteMediaCommand;
import com.doryann.flowpilot.command.api.media.UpdateMediaCommand;
import com.doryann.flowpilot.event.media.MediaCreatedEvent;
import com.doryann.flowpilot.event.media.MediaDeletedEvent;
import com.doryann.flowpilot.event.media.MediaStatusChangedEvent;
import com.doryann.flowpilot.event.media.MediaUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class MediaAggregate {

    @AggregateIdentifier
    private UUID mediaId;

    private String title;
    private MediaType mediaType;
    private MediaStatus status;
    private String platform;
    private Integer releaseYear;
    private String description;

    private boolean deleted;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    protected MediaAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public MediaAggregate(CreateMediaCommand command) {
        apply(
                MediaCreatedEvent.builder()
                        .mediaId(command.getMediaId())
                        .title(command.getTitle())
                        .status(command.getStatus())
                        .mediaType(command.getMediaType())
                        .platform(command.getPlatform())
                        .releaseYear(command.getReleaseYear())
                        .description(command.getDescription())
                        .createdAt(now())
                        .build()
        );
    }

    @CommandHandler
    public void handle(UpdateMediaCommand command) {
        assertNotDeleted();

        apply(
                MediaUpdatedEvent.builder()
                        .mediaId(command.getMediaId())
                        .title(command.getTitle())
                        .status(command.getStatus())
                        .mediaType(command.getMediaType())
                        .platform(command.getPlatform())
                        .releaseYear(command.getReleaseYear())
                        .description(command.getDescription())
                        .updatedAt(now())
                        .build()
        );
    }

    @CommandHandler
    public void handle(ChangeMediaStatusCommand command) {
        assertNotDeleted();

        if (this.status == command.getStatus()) {
            return;
        }

        apply(
                MediaStatusChangedEvent.builder()
                        .mediaId(command.getMediaId())
                        .status(command.getStatus())
                        .updatedAt(now())
                        .build()
        );
    }

    @CommandHandler
    public void handle(DeleteMediaCommand command) {
        assertNotDeleted();

        apply(
                MediaDeletedEvent.builder()
                        .mediaId(command.getMediaId())
                        .deletedAt(now())
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(MediaCreatedEvent event) {
        this.mediaId = event.getMediaId();
        this.title = event.getTitle();
        this.status = event.getStatus();
        this.mediaType = event.getMediaType();
        this.platform = event.getPlatform();
        this.releaseYear = event.getReleaseYear();
        this.description = event.getDescription();
        this.createdAt = event.getCreatedAt();
        this.deleted = false;
    }

    @EventSourcingHandler
    public void on(MediaUpdatedEvent event) {
        this.title = event.getTitle();
        this.status = event.getStatus();
        this.mediaType = event.getMediaType();
        this.platform = event.getPlatform();
        this.releaseYear = event.getReleaseYear();
        this.description = event.getDescription();
        this.updatedAt = event.getUpdatedAt();
    }

    @EventSourcingHandler
    public void on(MediaStatusChangedEvent event) {
        this.status = event.getStatus();
        this.updatedAt = event.getUpdatedAt();
    }

    @EventSourcingHandler
    public void on(MediaDeletedEvent event) {
        this.deleted = true;
        this.updatedAt = event.getDeletedAt();
    }

    private void assertNotDeleted() {
        if (deleted) {
            throw new IllegalStateException("Media is deleted");
        }
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}