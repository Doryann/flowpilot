package com.doryann.flowpilot.mapper;

import com.doryann.flowpilot.api.model.MediaResponse;
import com.doryann.flowpilot.entity.MediaView;
import com.doryann.flowpilot.event.media.MediaCreatedEvent;
import com.doryann.flowpilot.event.media.MediaUpdatedEvent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaResponse toResponse(MediaView mediaView);

    List<MediaResponse> toResponses(List<MediaView> mediaViews);

    MediaView toView(MediaCreatedEvent event);

    MediaView toView(MediaUpdatedEvent event);
}
