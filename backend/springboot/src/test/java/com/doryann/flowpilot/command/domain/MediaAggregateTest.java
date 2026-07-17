package com.doryann.flowpilot.command.domain;

import com.doryann.flowpilot.api.model.MediaStatus;
import com.doryann.flowpilot.api.model.MediaType;
import com.doryann.flowpilot.command.api.media.CreateMediaCommand;
import com.doryann.flowpilot.command.api.media.UpdateMediaCommand;
import com.doryann.flowpilot.event.media.MediaCreatedEvent;
import com.doryann.flowpilot.event.media.MediaDeletedEvent;
import com.doryann.flowpilot.event.media.MediaUpdatedEvent;
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

class MediaAggregateTest {

    private FixtureConfiguration<MediaAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(MediaAggregate.class);
    }

    @Test
    void shouldCreateMedia() {
        UUID mediaId = UUID.randomUUID();

        CreateMediaCommand command = CreateMediaCommand.builder()
                .mediaId(mediaId)
                .title("Final Fantasy IX")
                .mediaType(MediaType.GAME)
                .status(MediaStatus.PLANNED)
                .platform("Steam")
                .releaseYear(2000)
                .description("Meilleur jeu de tous les temps")
                .build();

        fixture.givenNoPriorActivity()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(payload -> {
                            if (!(payload instanceof MediaCreatedEvent event)) {
                                return false;
                            }

                            return event.getMediaId().equals(mediaId)
                                    && event.getTitle().equals("Final Fantasy IX")
                                    && event.getMediaType() == MediaType.GAME
                                    && event.getStatus() == MediaStatus.PLANNED
                                    && event.getPlatform().equals("Steam")
                                    && event.getReleaseYear().equals(2000)
                                    && event.getCreatedAt() != null;
                        }),
                        andNoMore()
                )));
    }

    @Test
    void shouldUpdateExistingMedia() {
        UUID mediaId = UUID.randomUUID();

        MediaCreatedEvent createdEvent = MediaCreatedEvent.builder()
                .mediaId(mediaId)
                .title("Old title")
                .mediaType(MediaType.GAME)
                .status(MediaStatus.PLANNED)
                .platform("Steam")
                .releaseYear(2000)
                .description("Old description")
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        UpdateMediaCommand command = UpdateMediaCommand.builder()
                .mediaId(mediaId)
                .title("Final Fantasy IX")
                .mediaType(MediaType.GAME)
                .status(MediaStatus.COMPLETED)
                .platform("Steam")
                .releaseYear(2000)
                .description("Updated description")
                .build();

        fixture.given(createdEvent)
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(payload -> {
                            if (!(payload instanceof MediaUpdatedEvent event)) {
                                return false;
                            }

                            return event.getMediaId().equals(mediaId)
                                    && event.getTitle().equals("Final Fantasy IX")
                                    && event.getStatus() == MediaStatus.COMPLETED
                                    && event.getUpdatedAt() != null;
                        }),
                        andNoMore()
                )));
    }

    @Test
    void shouldRejectUpdateWhenMediaIsDeleted() {
        UUID mediaId = UUID.randomUUID();

        MediaCreatedEvent createdEvent = MediaCreatedEvent.builder()
                .mediaId(mediaId)
                .title("Final Fantasy IX")
                .mediaType(MediaType.GAME)
                .status(MediaStatus.PLANNED)
                .platform("Steam")
                .releaseYear(2000)
                .description("Meilleur jeu de tous les temps")
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        MediaDeletedEvent deletedEvent = MediaDeletedEvent.builder()
                .mediaId(mediaId)
                .deletedAt(OffsetDateTime.now(ZoneOffset.UTC))
                .build();

        UpdateMediaCommand command = UpdateMediaCommand.builder()
                .mediaId(mediaId)
                .title("Should fail")
                .mediaType(MediaType.GAME)
                .status(MediaStatus.COMPLETED)
                .platform("Steam")
                .releaseYear(2000)
                .description("Should fail")
                .build();

        fixture.given(createdEvent, deletedEvent)
                .when(command)
                .expectException(IllegalStateException.class);
    }
}