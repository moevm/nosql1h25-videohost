package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reactions")
public class ReactionData {

    @Id
    private String id;

    @DBRef
    private UserData user;

    @DBRef
    private VideoData video;

    private ReactionEnum type;

    @CreatedDate
    private LocalDateTime timestamp;
}
