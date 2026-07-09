package com.doryann.flowpilot.query.handler;

import com.doryann.flowpilot.entity.MediaView;
import com.doryann.flowpilot.query.api.media.FindAllMediaQuery;
import com.doryann.flowpilot.query.api.media.FindMediaByIdQuery;
import com.doryann.flowpilot.repository.MediaViewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaQueryHandler {

    private final MediaViewRepository mediaViewRepository;

    @QueryHandler
    public List<MediaView> handle(FindAllMediaQuery query) {
        log.info("Handling FindAllMediaQuery");

        return mediaViewRepository.findAll();
    }

    @QueryHandler
    public MediaView handle(FindMediaByIdQuery query) {
        log.info("Handling FindMediaByIdQuery for mediaId={}", query.getMediaId());

        return mediaViewRepository.findById(query.getMediaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Media not found with id=" + query.getMediaId()
                ));
    }
}
