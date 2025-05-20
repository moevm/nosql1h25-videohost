package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ReactionsCount;
import org.example.exception.UserNotFoundException;
import org.example.exception.VideoNotFoundException;
import org.example.model.ReactionData;
import org.example.model.ReactionEnum;
import org.example.model.UserData;
import org.example.model.VideoData;
import org.example.repository.ReactionRepository;
import org.example.repository.UserRepository;
import org.example.repository.VideoRepository;
import org.example.service.api.ReactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final UserAuthServiceImpl userAuthService;

    @Override
    @Transactional
    public ReactionData addReaction(String userId, String videoId, ReactionEnum reaction) throws UserNotFoundException {

        VideoData videoData = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Optional<ReactionData> existingReaction = reactionRepository
                .findByUserAndVideo(userData, videoData);

        // Если реакция того же типа - удаляем
        if (existingReaction.isPresent() && existingReaction.get().getType() == reaction) {
            reactionRepository.delete(existingReaction.get());
            return null;
        }

        // Если реакция другого типа - заменяем
        existingReaction.ifPresent(reactionRepository::delete);

        ReactionData reactionData = ReactionData.builder()
                .type(reaction)
                .user(userData)
                .video(videoData)
                .build();

        return reactionRepository.save(reactionData);
    }

    @Override
    @Transactional
    public ReactionsCount getNumberVideoReactions(String videoId) {

        videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        ReactionsCount reactionsCount = new ReactionsCount();
        reactionsCount.setDislikeCount(reactionRepository.countByVideo_IdAndType(videoId, ReactionEnum.DISLIKE));
        reactionsCount.setLikeCount(reactionRepository.countByVideo_IdAndType(videoId, ReactionEnum.LIKE));

        return reactionsCount;
    }

    @Override
    @Transactional
    public ReactionEnum isUserSetReaction(String videoId) throws UserNotFoundException {
        String userId = userAuthService.getUserId();
        UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        VideoData videoData = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));

        Optional<ReactionData> existingReaction = reactionRepository
                .findByUserAndVideo(userData, videoData);

        return existingReaction.map(ReactionData::getType).orElse(null);
    }
}
