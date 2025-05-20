package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.*;
import org.example.model.SubscriptionData;
import org.example.model.UserData;
import org.example.repository.SubscriptionRepository;
import org.example.repository.UserRepository;
import org.example.service.api.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public void subscribe(String followerId, String followingId) throws UserNotFoundException {

        if (Objects.equals(followerId, followingId)) {
            throw new SubscribeYourselfException("You can't subscribe to yourself.");
        }

        UserData follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException(followerId));

        if (follower.isBlocked()) {
            throw new UserBannedException();
        }

        UserData following = userRepository.findById(followingId)
                .orElseThrow(() -> new UserNotFoundException(followingId));

        if (subscriptionRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new AlreadySubscribedException();
        }

        SubscriptionData subscription = SubscriptionData.builder()
                .follower(follower)
                .following(following)
                .build();

        following.setSubscriberCount(following.getSubscriberCount() + 1);
        userRepository.save(following);

        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public void unsubscribe(String followerId, String followingId) throws UserNotFoundException {
        UserData follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException(followerId));

        if (follower.isBlocked()) {
            throw new UserBannedException();
        }
        
        UserData following = userRepository.findById(followingId)
                .orElseThrow(() -> new UserNotFoundException(followingId));

        SubscriptionData subscription = subscriptionRepository
                .findByFollower_IdAndFollowing_Id(followerId, followingId)
                .orElseThrow(SubscriptionNotFoundException::new);

        subscriptionRepository.delete(subscription);

        following.setSubscriberCount(following.getSubscriberCount() - 1);
        userRepository.save(following);
    }

    // Получить подписки пользователя
    @Override
    @Transactional
    public List<UserData> getUserFollowings(String userId) throws UserNotFoundException {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return subscriptionRepository.findByFollower_Id(user.getId())
                .stream()
                .map(SubscriptionData::getFollowing)
                .toList();
    }

    // Получить подписчиков пользователя
    @Override
    @Transactional
    public List<UserData> getUserFollowers(String userId) throws UserNotFoundException {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return subscriptionRepository.findByFollowing_Id(user.getId())
                .stream()
                .map(SubscriptionData::getFollower)
                .toList();
    }

    @Override
    @Transactional
    public boolean isSubscribed(String followerId, String followingId) throws UserNotFoundException {

        UserData followerData = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException(followerId));

        UserData followingData = userRepository.findById(followingId)
                .orElseThrow(() -> new UserNotFoundException(followingId));

        return subscriptionRepository.findByFollowerAndFollowing(followerData, followingData).isPresent();
    }

    @Override
    public int getFollowersCount(String userId) {
        return subscriptionRepository.countByFollowing_Id(userId);
    }

    @Override
    public int getFollowingCount(String userId) {
        return subscriptionRepository.countByFollower_Id(userId);
    }
}
