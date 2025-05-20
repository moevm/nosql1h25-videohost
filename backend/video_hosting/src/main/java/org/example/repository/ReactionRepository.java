package org.example.repository;

import org.example.model.ReactionData;
import org.example.model.ReactionEnum;
import org.example.model.UserData;
import org.example.model.VideoData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends MongoRepository<ReactionData, String> {

    int countByVideo_IdAndType(String videoId, ReactionEnum type);

    Optional<ReactionData> findByUserAndVideo(UserData user, VideoData video);
}
