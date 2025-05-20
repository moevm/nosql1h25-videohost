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

import static org.example.dto.UserSearchCriteria.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
         USERNAME_PROPERTY, EMAIL_PROPERTY, REGISTERED_AFTER_PROPERTY, REGISTERED_BEFORE_PROPERTY, BLOCKED_PROPERTY, MIN_SUBSCRIBERS_PROPERTY, MAX_SUBSCRIBERS_PROPERTY
})
@JsonTypeName("UserSearchCriteria")
public class UserSearchCriteria {

    public static final String USERNAME_PROPERTY = "username";
    private String username;

    public static final String EMAIL_PROPERTY = "email";
    private String email;

    public static final String BLOCKED_PROPERTY = "blocked";
    private Boolean blocked;

    public static final String REGISTERED_AFTER_PROPERTY = "registeredAfter";
    private String registeredAfter;

    public static final String REGISTERED_BEFORE_PROPERTY = "registeredBefore";
    private String registeredBefore;

    public static final String MIN_SUBSCRIBERS_PROPERTY = "minSubscribers";
    private Integer minSubscribers;

    public static final String MAX_SUBSCRIBERS_PROPERTY = "maxSubscribers";
    private Integer maxSubscribers;

    public Query toQuery() {
        Query query = new Query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

        if (StringUtils.hasText(username)) {
            query.addCriteria(Criteria.where("username").regex(username, "i"));
        }

        if (StringUtils.hasText(email)) {
            query.addCriteria(Criteria.where("email").regex(email, "i"));
        }

        if (registeredAfter != null && !registeredAfter.isEmpty()) {
            LocalDateTime date = LocalDate.parse(registeredAfter, formatter).atStartOfDay();
            query.addCriteria(Criteria.where("uploadDate").gte(date));
        }

        if (registeredBefore != null && !registeredBefore.isEmpty()) {
            LocalDateTime date = LocalDate.parse(registeredBefore, formatter).atStartOfDay();
            query.addCriteria(Criteria.where("uploadDate").lte(date));
        }

        if (minSubscribers != null || maxSubscribers != null) {
            Criteria subsCriteria = Criteria.where("subscriberCount");
            if (minSubscribers != null) {
                subsCriteria.gte(minSubscribers);
            }
            if (maxSubscribers != null) {
                subsCriteria.lte(maxSubscribers);
            }
            query.addCriteria(subsCriteria);
        }

        if (blocked != null) {
            query.addCriteria(Criteria.where("blocked").is(blocked));
        }

        return query;
    }

    @JsonProperty(MAX_SUBSCRIBERS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getMaxSubscribers() {
        return maxSubscribers;
    }

    @JsonProperty(MAX_SUBSCRIBERS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setMaxSubscribers(Integer maxSubscribers) {
        this.maxSubscribers = maxSubscribers;
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

    @JsonProperty(BLOCKED_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Boolean getBlocked() {
        return blocked;
    }

    @JsonProperty(BLOCKED_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @JsonProperty(REGISTERED_AFTER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getRegisteredAfter() {
        return registeredAfter;
    }

    @JsonProperty(REGISTERED_AFTER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setRegisteredAfter(String registeredAfter) {
        this.registeredAfter = registeredAfter;
    }

    @JsonProperty(REGISTERED_BEFORE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getRegisteredBefore() {
        return registeredBefore;
    }

    @JsonProperty(REGISTERED_BEFORE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setRegisteredBefore(String registeredBefore) {
        this.registeredBefore = registeredBefore;
    }

    @JsonProperty(MIN_SUBSCRIBERS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Integer getMinSubscribers() {
        return minSubscribers;
    }

    @JsonProperty(MIN_SUBSCRIBERS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setMinSubscribers(Integer minSubscribers) {
        this.minSubscribers = minSubscribers;
    }
}
