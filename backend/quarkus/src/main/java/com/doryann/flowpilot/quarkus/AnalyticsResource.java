package com.doryann.flowpilot.analytics.web;

import com.doryann.flowpilot.analytics.api.AnalyticsApi;
import com.doryann.flowpilot.analytics.api.model.AnalyticsSummaryResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnalyticsResource implements AnalyticsApi {

    @Override
    public AnalyticsSummaryResponse getAnalyticsSummary() {
        return new AnalyticsSummaryResponse()
                .totalMedia(0)
                .completedMedia(0)
                .plannedMedia(0);
    }
}