package com.doryann.flowpilot.mapper;

import com.doryann.flowpilot.api.model.ReviewResponse;
import com.doryann.flowpilot.entity.ReviewView;
import com.doryann.flowpilot.event.review.ReviewCreatedEvent;
import com.doryann.flowpilot.event.review.ReviewUpdatedEvent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewView toView(ReviewCreatedEvent event);

    ReviewView toView(ReviewUpdatedEvent event);

    ReviewResponse toResponse(ReviewView reviewView);

    List<ReviewResponse> toResponses(List<ReviewView> reviewViews);
}
