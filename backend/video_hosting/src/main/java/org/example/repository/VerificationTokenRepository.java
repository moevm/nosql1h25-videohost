package org.example.repository;

import org.example.model.VerificationTokenData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationTokenData, String> {

    Optional<VerificationTokenData> findByToken(String token);
}
