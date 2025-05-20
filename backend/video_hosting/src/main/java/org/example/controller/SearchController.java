package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.SearchApi;
import org.example.controller.mapper.UserMapper;
import org.example.controller.mapper.VideoMapper;
import org.example.dto.UserDTO;
import org.example.dto.UserSearchCriteria;
import org.example.dto.VideoDTO;
import org.example.dto.VideoSearchCriteria;
import org.example.service.api.SearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
class SearchController implements SearchApi {

    private final SearchService searchService;
    private final UserMapper userMapper;
    private final VideoMapper videoMapper;

    @Override
    public List<UserDTO> searchUsers(UserSearchCriteria criteria) {
        return searchService.searchUsers(criteria).stream()
                .map(userMapper::map)
                .toList();
    }

    @Override
    public List<VideoDTO> searchVideos(VideoSearchCriteria criteria) {
        return searchService.searchVideos(criteria).stream()
                .map(videoMapper::map)
                .toList();
    }
}
