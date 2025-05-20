package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        AuthResponse.TOKEN_PROPERTY
})
@JsonTypeName("AuthResponse")
public class AuthResponse {

    public static final String TOKEN_PROPERTY = "token";
    private String token;

    @JsonProperty(TOKEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getToken() {
        return token;
    }

    @JsonProperty(TOKEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setToken(String token) {
        this.token = token;
    }
}
