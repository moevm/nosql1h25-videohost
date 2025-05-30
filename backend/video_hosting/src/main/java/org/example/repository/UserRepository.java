package org.example.repository;

import org.example.model.UserData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserData, String> {

    Optional<UserData> findByUsername(String username);

    Optional<UserData> findByEmail(String email);

    Page<UserData> findByBlockedFalse(Pageable pageable);
}
