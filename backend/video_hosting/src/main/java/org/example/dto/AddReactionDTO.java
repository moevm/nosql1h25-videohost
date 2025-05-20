package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.example.model.ReactionEnum;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        AddReactionDTO.TYPE_PROPERTY
})
@JsonTypeName("AddVideoDTO")
public class AddReactionDTO {

    public static final String TYPE_PROPERTY = "type";
    private ReactionEnum type;

    @JsonProperty(TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public ReactionEnum getType() {
        return type;
    }

    @JsonProperty(TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setType(ReactionEnum type) {
        this.type = type;
    }
}
