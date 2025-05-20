package org.example.service.api;

import org.example.exception.UserNotFoundException;
import org.example.model.UserData;

import java.util.List;

public interface SubscriptionService {

    void subscribe(String followerId, String followingId) throws UserNotFoundException;

    void unsubscribe(String followerId, String followingId) throws UserNotFoundException;

    List<UserData> getUserFollowings(String userId) throws UserNotFoundException;

    List<UserData> getUserFollowers(String userId) throws UserNotFoundException;

    boolean isSubscribed(String followerId, String followingId) throws UserNotFoundException;

    int getFollowersCount(String userId);

    int getFollowingCount(String userId);
}
