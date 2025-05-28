package org.example.repository;

import org.example.model.UserData;
import org.example.model.VideoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends MongoRepository<VideoData, String> {

    Optional<VideoData> findByIdAndUser(String id, UserData userData);

    Page<VideoData> findByUserInAndIsVideoHiddenFalse(List<UserData> userIds, Pageable pageable);

    List<VideoData> findByUserAndIsVideoHiddenFalse(UserData user);

    Page<VideoData> findByIsVideoHiddenFalse(Pageable pageable);

    List<VideoData> findByUser(UserData user);
}
