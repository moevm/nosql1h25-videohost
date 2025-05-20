package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.dto.VideoSearchCriteria.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_PROPERTY, DESCRIPTION_PROPERTY, TAGS_PROPERTY, UPLOADED_AFTER_PROPERTY, UPLOADED_BEFORE_PROPERTY, MIN_VIEWS_PROPERTY, HIDDEN_PROPERTY, MAX_VIEWS_PROPERTY
})
@JsonTypeName("UserSearchCriteria")
public class VideoSearchCriteria {

    public static final String TITLE_PROPERTY = "title";
    private String title;

    public static final String DESCRIPTION_PROPERTY = "description";
    private String description;

    public static final String TAGS_PROPERTY = "tags";
    private List<String> tags;

    public static final String UPLOADED_AFTER_PROPERTY = "uploadedAfter";
    private String uploadedAfter;

    public static final String UPLOADED_BEFORE_PROPERTY = "uploadedBefore";
    private String uploadedBefore;

    public static final String MIN_VIEWS_PROPERTY = "minViews";
    private Integer minViews;

    public static final String MAX_VIEWS_PROPERTY = "maxViews";
    private Integer maxViews;

    public static final String HIDDEN_PROPERTY = "hidden";
    private Boolean hidden;

    public Query toQuery() {
        Query query = new Query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

        if (StringUtils.hasText(title)) {
            query.addCriteria(Criteria.where("title").regex(title, "i"));
        }

        if (StringUtils.hasText(description)) {
            query.addCriteria(Criteria.where("description").regex(description, "i"));
        }

        if (uploadedAfter != null && !uploadedAfter.isEmpty()) {
            LocalDateTime date = LocalDate.parse(uploadedAfter, formatter).atStartOfDay();
            query.addCriteria(Criteria.where("uploadDate").gte(date));
        }

        if (uploadedBefore != null && !uploadedBefore.isEmpty()) {
            LocalDateTime date = LocalDate.parse(uploadedBefore, formatter).atStartOfDay();
            query.addCriteria(Criteria.where("uploadDate").lte(date));
        }

        if (minViews != null || maxViews != null) {
            Criteria viewsCriteria = Criteria.where("views");
            if (minViews != null) {
                viewsCriteria.gte(minViews);
            }
            if (maxViews != null) {
                viewsCriteria.lte(maxViews);
            }
            query.addCriteria(viewsCriteria);
        }

        if (tags != null && !tags.isEmpty()) {
            query.addCriteria(Criteria.where("tags").all(tags));
        }

        if (hidden != null) {
            query.addCriteria(Criteria.where("isVideoHidden").is(hidden));
        }

        return query;
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
