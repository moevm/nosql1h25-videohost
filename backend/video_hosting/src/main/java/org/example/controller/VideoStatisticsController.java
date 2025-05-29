package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.example.controller.api.VideoStatisticsApi;
import org.example.dto.ChartConfig;
import org.example.dto.ChartType;
import org.example.dto.VideoFilter;
import org.example.service.api.VideoStatisticsService;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class VideoStatisticsController implements VideoStatisticsApi {

    private final VideoStatisticsService service;

    @Override
    public ResponseEntity<?> getVideoStatistics(VideoFilter filter, String xAxis, String yAxis, ChartType chartType) {
        ChartConfig config = new ChartConfig();
        config.setxAxisAttribute(xAxis);
        config.setyAxisAttribute(yAxis);
        config.setChartType(chartType);

        AggregationResults<Document> results = service.getAggregatedVideoData(filter, config);

        return ResponseEntity.ok(results.getMappedResults());
    }
}
