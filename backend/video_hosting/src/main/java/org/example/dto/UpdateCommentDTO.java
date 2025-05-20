package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import static org.example.dto.UpdateCommentDTO.TEXT_PROPERTY;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TEXT_PROPERTY
})
@JsonTypeName("UpdateCommentDTO")
public class UpdateCommentDTO {

    public static final String TEXT_PROPERTY = "text";
    private String text;

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
