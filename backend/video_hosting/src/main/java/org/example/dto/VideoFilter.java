package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import static org.example.dto.VideoFilter.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_CONTAINS_PROPERTY, DESCRIPTION_CONTAINS_PROPERTY, MIN_VIEWS_PROPERTY, MAX_VIEWS_PROPERTY,
        UPLOADED_AFTER_PROPERTY, UPLOADED_BEFORE_PROPERTY, TAGS_PROPERTY, HIDDEN_PROPERTY
})
@JsonTypeName("VideoFilter")
public class VideoFilter {

    public static final String TITLE_CONTAINS_PROPERTY = "titleContains";
    private String titleContains;

    public static final String DESCRIPTION_CONTAINS_PROPERTY = "descriptionContains";
    private String descriptionContains;

    public static final String MIN_VIEWS_PROPERTY = "minViews";
    private Integer minViews;

    public static final String MAX_VIEWS_PROPERTY = "maxViews";
    private Integer maxViews;

    public static final String UPLOADED_AFTER_PROPERTY = "uploadedAfter";
    private String uploadedAfter;

    public static final String UPLOADED_BEFORE_PROPERTY = "uploadedBefore";
    private String uploadedBefore;

    public static final String TAGS_PROPERTY = "tags";
    private List<String> tags;

    public static final String HIDDEN_PROPERTY = "hidden";
    private Boolean hidden;

    @JsonProperty(TITLE_CONTAINS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getTitleContains() {
        return titleContains;
    }

    @JsonProperty(TITLE_CONTAINS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }

    @JsonProperty(DESCRIPTION_CONTAINS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getDescriptionContains() {
        return descriptionContains;
    }

    @JsonProperty(DESCRIPTION_CONTAINS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setDescriptionContains(String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }

    @JsonProperty(MIN_VIEWS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getMinViews() {
        return minViews;
    }

    @JsonProperty(MIN_VIEWS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setMinViews(Integer minViews) {
        this.minViews = minViews;
    }

    @JsonProperty(MAX_VIEWS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getMaxViews() {
        return maxViews;
    }

    @JsonProperty(MAX_VIEWS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setMaxViews(Integer maxViews) {
        this.maxViews = maxViews;
    }

    @JsonProperty(UPLOADED_AFTER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getUploadedAfter() {
        return uploadedAfter;
    }

    @JsonProperty(UPLOADED_AFTER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUploadedAfter(String uploadedAfter) {
        this.uploadedAfter = uploadedAfter;
    }

    @JsonProperty(UPLOADED_BEFORE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getUploadedBefore() {
        return uploadedBefore;
    }

    @JsonProperty(UPLOADED_BEFORE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUploadedBefore(String uploadedBefore) {
        this.uploadedBefore = uploadedBefore;
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

    @JsonProperty(HIDDEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Boolean getHidden() {
        return hidden;
    }

    @JsonProperty(HIDDEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
