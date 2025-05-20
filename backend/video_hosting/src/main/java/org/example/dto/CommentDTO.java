package org.example.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

import static org.example.dto.CommentDTO.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        COMMENT_ID_PROPERTY, TEXT_PROPERTY, USER_PROPERTY,
        CREATION_DATE_PROPERTY, AUTHOR_PROPERTY, IS_USER_COMMENT_PROPERTY
})
@JsonTypeName("CommentDTO")
public class CommentDTO {

    public static final String COMMENT_ID_PROPERTY = "commentId";
    private String commentId;

    public static final String TEXT_PROPERTY = "text";
    private String text;

    public static final String USER_PROPERTY = "userId";
    private String userId;

    public static final String AUTHOR_PROPERTY = "author";
    private String author;

    public static final String CREATION_DATE_PROPERTY = "creationDate";
    private LocalDateTime creationDate;

    public static final String IS_USER_COMMENT_PROPERTY = "isUserComment";
    private boolean isUserComment;

    @JsonProperty(IS_USER_COMMENT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public boolean isUserComment() {
        return isUserComment;
    }

    @JsonProperty(IS_USER_COMMENT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUserComment(boolean userComment) {
        isUserComment = userComment;
    }

    @JsonProperty(AUTHOR_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getAuthor() {
        return author;
    }

    @JsonProperty(AUTHOR_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty(USER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getUserId() {
        return userId;
    }

    @JsonProperty(USER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty(COMMENT_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getCommentId() {
        return commentId;
    }

    @JsonProperty(COMMENT_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @JsonProperty(CREATION_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @JsonProperty(CREATION_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty(TEXT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getText() {
        return text;
    }

    @JsonProperty(TEXT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setText(String text) {
        this.text = text;
    }
}
