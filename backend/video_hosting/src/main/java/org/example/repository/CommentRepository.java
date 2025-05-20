package org.example.repository;

import org.example.model.CommentData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<CommentData, String> {

    List<CommentData> findByVideo_Id(String videoId);
}
