package org.example.service.api;

import org.example.dto.ReactionsCount;
import org.example.exception.UserNotFoundException;
import org.example.model.ReactionData;
import org.example.model.ReactionEnum;

public interface ReactionService {

    ReactionData addReaction(String userId, String videoId, ReactionEnum reaction) throws UserNotFoundException;

    ReactionsCount getNumberVideoReactions(String videoId);

    ReactionEnum isUserSetReaction(String videoId) throws UserNotFoundException;
}
