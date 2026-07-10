package com.doryann.flowpilot.controller;

import com.doryann.flowpilot.api.ReviewQueryApi;
import com.doryann.flowpilot.api.model.ReviewResponse;
import com.doryann.flowpilot.entity.ReviewView;
import com.doryann.flowpilot.mapper.ReviewMapper;
import com.doryann.flowpilot.query.api.review.FindAllReviewQuery;
import com.doryann.flowpilot.query.api.review.FindReviewByIdQuery;
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
public class ReviewQueryController implements ReviewQueryApi {
    private final QueryGateway queryGateway;
    private final ReviewMapper reviewMapper;

    @Override
    public ResponseEntity<List<ReviewResponse>> findAllReview() {
        log.info("Find all reviews");

        List<ReviewView> reviewViews = queryGateway.query(
                FindAllReviewQuery.builder().build(),
                ResponseTypes.multipleInstancesOf(ReviewView.class)
        ).join();

        return ResponseEntity.ok(reviewMapper.toResponses(reviewViews));
    }

    @Override
    public ResponseEntity<ReviewResponse> findReviewById(UUID reviewId) {
        log.info("Find review by id={}", reviewId);

        ReviewView reviewView = queryGateway.query(FindReviewByIdQuery.builder().reviewId(reviewId).build(),
                ResponseTypes.instanceOf(ReviewView.class)
        ).join();

        return ResponseEntity.ok(reviewMapper.toResponse(reviewView));
    }
}
