package org.example.service.api;

import org.example.dto.UserSearchCriteria;
import org.example.dto.VideoSearchCriteria;
import org.example.model.UserData;
import org.example.model.VideoData;

import java.util.List;

public interface SearchService {
    List<UserData> searchUsers(UserSearchCriteria criteria);

    List<VideoData> searchVideos(VideoSearchCriteria criteria);
}
