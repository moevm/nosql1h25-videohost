package org.example.service.api;

import org.bson.Document;
import org.example.dto.ChartConfig;
import org.example.dto.VideoFilter;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface VideoStatisticsService {

    AggregationResults<Document> getAggregatedVideoData(VideoFilter filter, ChartConfig config);
}
