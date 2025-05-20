package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserSearchCriteria;
import org.example.dto.VideoSearchCriteria;
import org.example.model.UserData;
import org.example.model.VideoData;
import org.example.service.api.SearchService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class SearchServiceImpl implements SearchService {

    private final MongoTemplate mongoTemplate;

    public List<UserData> searchUsers(UserSearchCriteria criteria) {
        return mongoTemplate.find(criteria.toQuery(), UserData.class);
    }

    public List<VideoData> searchVideos(VideoSearchCriteria criteria) {
        return mongoTemplate.find(criteria.toQuery(), VideoData.class);
    }

    private void validateSearchCriteria(Object criteria) {
        boolean allNull = Arrays.stream(criteria.getClass().getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .allMatch(f -> {
                    try {
                        return f.get(criteria) == null;
                    } catch (IllegalAccessException e) {
                        return true;
                    }
                });

        if (allNull) {
            throw new IllegalArgumentException("At least one search parameter must be specified");
        }
    }
}
