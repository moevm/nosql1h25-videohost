package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("applicationData")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationData {
    private List<UserData> users;
    private List<VideoData> videos;
    private List<SubscriptionData> subscriptions;
    private List<CommentData> comments;
    private List<ReactionData> reactions;
}
