package pl.aitwar.hivemind.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.aitwar.hivemind.domain.enumeration.MovieState;

import java.time.OffsetDateTime;

/**
 * Movie document used to store information about movie itself and ratings
 *
 * @author Szymon Chmal
 */

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

    @CreatedDate
    private OffsetDateTime creationDate;
}
