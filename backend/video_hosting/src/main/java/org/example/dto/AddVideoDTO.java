package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

import static org.example.dto.AddVideoDTO.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_PROPERTY, DESCRIPTION_PROPERTY, TAGS_PROPERTY
})
@JsonTypeName("AddVideoDTO")
public class AddVideoDTO {

    public static final String TITLE_PROPERTY = "title";
    private String title;

    public static final String DESCRIPTION_PROPERTY = "description";
    private String description;

    public static final String TAGS_PROPERTY = "tags";
    private List<String> tags;

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
}
