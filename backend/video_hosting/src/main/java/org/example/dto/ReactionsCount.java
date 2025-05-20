package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import static org.example.dto.ReactionsCount.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        LIKE_COUNT_PROPERTY, DISLIKE_COUNT_PROPERTY
})
@JsonTypeName("ReactionsCount")
public class ReactionsCount {

    public static final String LIKE_COUNT_PROPERTY = "likeCount";
    private int likeCount;

    public static final String DISLIKE_COUNT_PROPERTY = "dislikeCount";
    private int dislikeCount;

    @JsonProperty(LIKE_COUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public int getLikeCount() {
        return likeCount;
    }

    @JsonProperty(LIKE_COUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @JsonProperty(DISLIKE_COUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public int getDislikeCount() {
        return dislikeCount;
    }

    @JsonProperty(DISLIKE_COUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
