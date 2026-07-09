package com.doryann.flowpilot.query.projection;

import com.doryann.flowpilot.event.media.*;
import com.doryann.flowpilot.mapper.MediaMapper;
import com.doryann.flowpilot.repository.MediaViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
@ProcessingGroup("media-projections")
public class MediaProjection {

    private final MediaViewRepository mediaViewRepository;
    private final MediaMapper mediaMapper;

    @EventHandler
    public void on(MediaCreatedEvent event) {
        log.info("Projecting MediaCreatedEvent for mediaId={}", event.getMediaId());
        mediaViewRepository.save(mediaMapper.toView(event));
    }

    @EventHandler
    public void on(MediaUpdatedEvent event) {
        log.info("Projecting MediaUpdatedEvent for mediaId={}", event.getMediaId());
        mediaViewRepository.save(mediaMapper.toView(event));
    }

    @EventHandler
    public void on(MediaStatusChangedEvent event) {
        log.info("Projecting MediaStatusChangedEvent for mediaId={}", event.getMediaId());
        int updatedRows = mediaViewRepository.updateStatusById(
                event.getMediaId(),
                event.getStatus().getValue()
        );

        if (updatedRows == 0) {
            throw new IllegalStateException(
                    "MediaView not found for mediaId=" + event.getMediaId()
            );
        }
    }

    @EventHandler
    public void on(RateMediaEvent event) {
        log.info("Projecting MediaStatusChangedEvent for mediaId={}", event.getMediaId());
        int updatedRows = mediaViewRepository.updateRatingById(
                event.getMediaId(),
                event.getRating()
        );

        if (updatedRows == 0) {
            throw new IllegalStateException(
                    "MediaView not found for mediaId=" + event.getMediaId()
            );
        }
    }

    @EventHandler
    public void on(MediaDeletedEvent event) {
        log.info("Projecting media deletion for mediaId={}", event.getMediaId());

        mediaViewRepository.deleteById(event.getMediaId());
    }
}
