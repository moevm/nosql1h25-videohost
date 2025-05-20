package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonTypeName("ErrorResponse")
public class ErrorResponse {

    public static final String MESSAGE_PROPERTY = "message";
    private String message;

    @JsonProperty(MESSAGE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "User already exists")
    public String getMessage() {
        return message;
    }
}