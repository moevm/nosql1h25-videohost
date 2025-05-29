package org.example.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.example.dto.ChartConfig;
import org.example.dto.VideoFilter;
import org.example.model.VideoData;
import org.example.service.api.VideoStatisticsService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class VideoStatisticsServiceImpl implements VideoStatisticsService {

    private final MongoTemplate mongoTemplate;

    @Override
    public AggregationResults<Document> getAggregatedVideoData(VideoFilter filter, ChartConfig config) {
        List<AggregationOperation> operations = new ArrayList<>();

        // Step 1: Add match operations for filtering
        operations.add(buildMatchOperation(filter));

        // Step 2: Add group operations based on chart config
        operations.add(buildGroupOperation(config));

        // Step 3: Add sort operation if needed
        operations.add(Aggregation.sort(Sort.Direction.ASC, "_id"));

        TypedAggregation<Document> aggregation = Aggregation.newAggregation(
                Document.class,
                operations
        );

        return mongoTemplate.aggregate(aggregation, VideoData.class, Document.class);
    }

    private AggregationOperation buildMatchOperation(VideoFilter filter) {
        Criteria criteria = new Criteria();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

        if (filter.getTitleContains() != null) {
            criteria.and("title").regex(filter.getTitleContains(), "i");
        }
        if (filter.getDescriptionContains() != null) {
            criteria.and("description").regex(filter.getDescriptionContains(), "i");
        }
        if (filter.getMinViews() != null) {
            criteria.and("views").gte(filter.getMinViews());
        }
        if (filter.getMaxViews() != null) {
            criteria.and("views").lte(filter.getMaxViews());
        }

        if (filter.getUploadedAfter() != null || filter.getUploadedBefore() != null) {
            criteria.and("uploadDate");

            if (filter.getUploadedAfter() != null) {
                LocalDateTime date = LocalDate.parse(filter.getUploadedAfter(), formatter).atStartOfDay();
                criteria.gte(date);
            }

            if (filter.getUploadedBefore() != null) {
                LocalDateTime beforeDate = LocalDate.parse(filter.getUploadedBefore(), formatter).atTime(LocalTime.MAX);
                criteria.lte(beforeDate);
            }
        }

        if (filter.getTags() != null && !filter.getTags().isEmpty()) {
            criteria.and("tags").in(filter.getTags());
        }
        if (filter.getHidden() != null) {
            criteria.and("isVideoHidden").is(filter.getHidden());
        }

        return Aggregation.match(criteria);
    }

    private AggregationOperation buildGroupOperation(ChartConfig config) {
        String xField = config.getxAxisAttribute();
        String yField = config.getyAxisAttribute();

        // Simple grouping - you might need more complex logic here
        return Aggregation.group(xField)
                .count().as("count")
                .push(yField).as("yValues");
    }
}
