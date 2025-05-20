package org.example.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.example.model.RoleEnum;

import java.time.LocalDateTime;
import java.util.Set;

import static org.example.dto.UserDTO.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    USER_ID_PROPERTY, USERNAME_PROPERTY, EMAIL_PROPERTY,
        REGISTRATION_DATE_PROPERTY, SUBSCRIBER_COUNT_PROPERTY,
        BLOCKED_PROPERTY, ROLES_PROPERTY, IS_USER_SUBSCRIBE_PROPERTY
})
@JsonTypeName("UserDTO")
public class UserDTO {

    public static final String USER_ID_PROPERTY = "userId";
    private String userId;

    public static final String USERNAME_PROPERTY = "username";
    private String username;

    public static final String EMAIL_PROPERTY = "email";
    private String email;

    public static final String REGISTRATION_DATE_PROPERTY = "registrationDate";
    private LocalDateTime registrationDate;

    public static final String SUBSCRIBER_COUNT_PROPERTY = "subscriberCount";
    private Integer subscriberCount;

    public static final String BLOCKED_PROPERTY = "blocked";
    private boolean blocked;

    public static final String ROLES_PROPERTY = "roles";
    private Set<RoleEnum> roles;

    public static final String IS_USER_SUBSCRIBE_PROPERTY = "isUserSubscribe";
    private boolean isUserSubscribe;

    @JsonProperty(IS_USER_SUBSCRIBE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public boolean isUserSubscribe() {
        return isUserSubscribe;
    }

    @JsonProperty(IS_USER_SUBSCRIBE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUserSubscribe(boolean userSubscribe) {
        isUserSubscribe = userSubscribe;
    }

    @JsonProperty(ROLES_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Set<RoleEnum> getRoles() {
        return roles;
    }

    @JsonProperty(ROLES_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
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

    @JsonProperty(REGISTRATION_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    @JsonProperty(REGISTRATION_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonProperty(SUBSCRIBER_COUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    @JsonProperty(SUBSCRIBER_COUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    @JsonProperty(BLOCKED_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public boolean isBlocked() {
        return blocked;
    }

    @JsonProperty(BLOCKED_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
