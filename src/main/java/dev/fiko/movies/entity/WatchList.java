package dev.fiko.movies.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "watchlists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {
    @Id
    private ObjectId id;
    private String userId;
    private List<Movie> movies;
}
