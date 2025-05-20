package org.example.repository;

import org.example.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserData, String> {

    Optional<UserData> findByUsername(String username);

    Optional<Object> findByEmail(String email);
}
