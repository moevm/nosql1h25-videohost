package org.example.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.example.model.ReactionEnum;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.dto.VideoDTO.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        USER_ID_PROPERTY, TITLE_PROPERTY, DESCRIPTION_PROPERTY,
        S3KEY_PROPERTY, VIEWS_PROPERTY, COMMENTS_PROPERTY,
        REACTIONS_PROPERTY, UPLOAD_DATE_PROPERTY,
        TAGS_PROPERTY, IS_VIDEO_HIDDEN_PROPERTY, VIDEO_ID_PROPERTY,
        AUTHOR_PROPERTY, USER_REACTION_PROPERTY, FILE_NAME_PROPERTY
})
@JsonTypeName("VideoDTO")
public class VideoDTO {

    public static final String VIDEO_ID_PROPERTY = "videoId";
    private String videoId;

    public static final String USER_ID_PROPERTY = "userId";
    private String userId;

    public static final String AUTHOR_PROPERTY = "author";
    private String author;

    public static final String TITLE_PROPERTY = "title";
    private String title;

    public static final String DESCRIPTION_PROPERTY = "description";
    private String description;

    public static final String S3KEY_PROPERTY = "s3Key";
    private String s3Key;

    public static final String VIEWS_PROPERTY = "views";
    private int views;

    public static final String COMMENTS_PROPERTY = "comments";
    private List<CommentDTO> comments;

    public static final String REACTIONS_PROPERTY = "reactions";
    private ReactionsCount reactions;

    public static final String UPLOAD_DATE_PROPERTY = "uploadDate";
    private LocalDateTime uploadDate;

    public static final String TAGS_PROPERTY = "tags";
    private List<String> tags;

    public static final String IS_VIDEO_HIDDEN_PROPERTY = "isVideoHidden";
    private boolean isVideoHidden;

    public static final String USER_REACTION_PROPERTY = "userReaction";
    private ReactionEnum userReaction;

    public static final String FILE_NAME_PROPERTY = "fileName";
    private String fileName;

    @JsonProperty(FILE_NAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getFileName() {
        return fileName;
    }

    @JsonProperty(FILE_NAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty(USER_REACTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public ReactionEnum getUserReaction() {
        return userReaction;
    }

    @JsonProperty(USER_REACTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUserReaction(ReactionEnum userReaction) {
        this.userReaction = userReaction;
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

    @JsonProperty(VIDEO_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getVideoId() {
        return videoId;
    }

    @JsonProperty(VIDEO_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @JsonProperty(COMMENTS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public List<CommentDTO> getComments() {
        return comments;
    }

    @JsonProperty(COMMENTS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    @JsonProperty(REACTIONS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public ReactionsCount getReactions() {
        return reactions;
    }

    @JsonProperty(REACTIONS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setReactions(ReactionsCount reactions) {
        this.reactions = reactions;
    }

    @JsonProperty(USER_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getUserId() {
        return userId;
    }

    @JsonProperty(USER_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getTitle() {
        return title;
    }

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty(DESCRIPTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getDescription() {
        return description;
    }

    @JsonProperty(DESCRIPTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(S3KEY_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getS3Key() {
        return s3Key;
    }

    @JsonProperty(S3KEY_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    @JsonProperty(VIEWS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public int getViews() {
        return views;
    }

    @JsonProperty(VIEWS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setViews(int views) {
        this.views = views;
    }

    @JsonProperty(UPLOAD_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    @JsonProperty(UPLOAD_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    @JsonProperty(TAGS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty(TAGS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @JsonProperty(IS_VIDEO_HIDDEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public boolean isVideoHidden() {
        return isVideoHidden;
    }

    @JsonProperty(IS_VIDEO_HIDDEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setVideoHidden(boolean videoHidden) {
        isVideoHidden = videoHidden;
    }
}
