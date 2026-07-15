package com.doryann.flowpilot.service;

import com.doryann.flowpilot.repository.ReviewViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewViewRepository reviewViewRepository;

    @Transactional(readOnly = true)
    public boolean existsByMediaIdAndUserId(UUID mediaId, UUID userId) {
        return reviewViewRepository.existsByMediaIdAndUserId(mediaId, userId);
    }
}
