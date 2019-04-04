package pl.aitwar.hivemind.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    @NonNull
    private Long _id;

    private String name;

    @NonNull
    private MovieState state = MovieState.WAITING;
}
