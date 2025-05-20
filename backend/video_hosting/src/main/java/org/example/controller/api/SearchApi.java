package org.example.controller.api;

import jakarta.validation.Valid;
import org.example.dto.UserDTO;
import org.example.dto.UserSearchCriteria;
import org.example.dto.VideoDTO;
import org.example.dto.VideoSearchCriteria;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/search", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SearchApi {

    @GetMapping("/users")
    List<UserDTO> searchUsers(@Valid UserSearchCriteria criteria);

    @GetMapping("/videos")
    List<VideoDTO> searchVideos(@Valid VideoSearchCriteria criteria);
}
