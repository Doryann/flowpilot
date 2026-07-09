package com.doryann.flowpilot.controller;

import com.doryann.flowpilot.api.MediaQueryApi;
import com.doryann.flowpilot.api.model.MediaResponse;
import com.doryann.flowpilot.entity.MediaView;
import com.doryann.flowpilot.mapper.MediaMapper;
import com.doryann.flowpilot.query.api.media.FindAllMediaQuery;
import com.doryann.flowpilot.query.api.media.FindMediaByIdQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MediaQueryController implements MediaQueryApi {

    private final QueryGateway queryGateway;
    private final MediaMapper mediaMapper;

    @Override
    public ResponseEntity<List<MediaResponse>> findAllMedia() {
        log.info("Find all media");

        List<MediaView> mediaViews = queryGateway.query(
                FindAllMediaQuery.builder().build(),
                ResponseTypes.multipleInstancesOf(MediaView.class)
        ).join();

        return ResponseEntity.ok(mediaMapper.toResponses(mediaViews));
    }

    @Override
    public ResponseEntity<MediaResponse> findMediaById(UUID mediaId) {
        log.info("Find media by id={}", mediaId);

        MediaView mediaView = queryGateway.query(FindMediaByIdQuery.builder().mediaId(mediaId).build(),
                ResponseTypes.instanceOf(MediaView.class)
        ).join();

        return ResponseEntity.ok(mediaMapper.toResponse(mediaView));
    }
}