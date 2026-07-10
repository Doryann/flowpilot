package com.doryann.flowpilot.command.domain;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class ReviewAggregate {
    @AggregateIdentifier
    private UUID reviewId;
}
