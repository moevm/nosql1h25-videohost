package org.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "videos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoData {

    @Id
    private String id;

    @DBRef
    private UserData user;

    private String title;

    private String description;

    private String s3Key;

    private int views;

    @CreatedDate
    private LocalDateTime uploadDate;

    private List<String> tags;

    @JsonAlias("isVideoHidden")
    @JsonProperty("isVideoHidden")
    private boolean isVideoHidden = false;

    private String filename;
}
