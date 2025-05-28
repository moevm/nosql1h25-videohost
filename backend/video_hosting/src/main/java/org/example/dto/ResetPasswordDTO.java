package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static org.example.dto.ResetPasswordDTO.PASSWORD_PROPERTY;

@ToString
@EqualsAndHashCode
@AllArgsConstructor()
@NoArgsConstructor
@JsonPropertyOrder({
        PASSWORD_PROPERTY
})
@JsonTypeName("ResetPasswordDTO")
public class ResetPasswordDTO {

    public static final String PASSWORD_PROPERTY = "password";
    @NotBlank
    private String password;

    @JsonProperty(PASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getPassword() {
        return password;
    }

    @JsonProperty(PASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setPassword(String password) {
        this.password = password;
    }
}
