package org.example.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ToString
@EqualsAndHashCode
@AllArgsConstructor()
@NoArgsConstructor
@JsonPropertyOrder({
        AuthRequest.USERNAME_PROPERTY, AuthRequest.PASSWORD_PROPERTY, AuthRequest.EMAIL_PROPERTY
})
@JsonTypeName("RegistrationDTO")
public class AuthRequest {

    @NotBlank
    public static final String USERNAME_PROPERTY = "username";
    private String username;

    @NotBlank
    public static final String PASSWORD_PROPERTY = "password";
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank
    public static final String EMAIL_PROPERTY = "email";
    private String email;

    @JsonProperty(EMAIL_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getEmail() {
        return email;
    }

    @JsonProperty(EMAIL_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(USERNAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getUsername() {
        return username;
    }

    @JsonProperty(USERNAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUsername(String username) {
        this.username = username;
    }

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
