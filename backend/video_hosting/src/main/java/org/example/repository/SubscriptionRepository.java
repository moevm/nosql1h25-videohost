package org.example.repository;

import org.example.model.SubscriptionData;
import org.example.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends MongoRepository<SubscriptionData, String> {

    boolean existsByFollowerAndFollowing(UserData follower, UserData following);

    Optional<SubscriptionData> findByFollowerAndFollowing(UserData follower, UserData following);

    Optional<SubscriptionData> findByFollower_IdAndFollowing_Id(String followerId, String followingId);

    // Найти все подписки пользователя (на кого он подписан)
    List<SubscriptionData> findByFollower_Id(String followerId);

    // Найти всех подписчиков пользователя (кто подписан на него)
    List<SubscriptionData> findByFollowing_Id(String followingId);

    int countByFollowing_Id(String followingId); // Количество подписчиков

    int countByFollower_Id(String followerId);   // Количество подписок
}
