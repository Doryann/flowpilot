package com.doryann.flowpilot.repository;

import com.doryann.flowpilot.entity.ReviewView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewViewRepository extends JpaRepository<ReviewView, UUID> {
}
