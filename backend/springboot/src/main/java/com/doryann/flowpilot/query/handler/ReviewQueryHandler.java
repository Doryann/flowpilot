package com.doryann.flowpilot.query.handler;

import com.doryann.flowpilot.entity.ReviewView;
import com.doryann.flowpilot.query.api.review.FindAllReviewQuery;
import com.doryann.flowpilot.query.api.review.FindReviewByIdQuery;
import com.doryann.flowpilot.repository.ReviewViewRepository;
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
public class ReviewQueryHandler {
    private final ReviewViewRepository reviewViewRepository;

    @QueryHandler
    public List<ReviewView> handle(FindAllReviewQuery query) {
        log.info("Handling FindAllReviewQuery");

        return reviewViewRepository.findAll();
    }

    @QueryHandler
    public ReviewView handle(FindReviewByIdQuery query) {
        log.info("Handling FindReviewByIdQuery for reviewId={}", query.getReviewId());

        return reviewViewRepository.findById(query.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Review not found with id=" + query.getReviewId()
                ));
    }
}
