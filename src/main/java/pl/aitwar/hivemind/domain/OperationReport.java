package pl.aitwar.hivemind.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.aitwar.hivemind.domain.enumeration.MovieState;

import java.time.LocalDateTime;

/**
 * Operation document use to store information about movie state transitions
 *
 * @author Szymon Chmal
 */

@Data
@NoArgsConstructor
@Document(collection = "operation_reports")
public class OperationReport {
    @Id
    @NonNull
    @Accessors(prefix = "_")
    private ObjectId _id;

    private Long movie;
    private MovieState from;
    private MovieState to;

    private LocalDateTime date = LocalDateTime.now();

    public OperationReport(Long movie, MovieState from, MovieState to) {
        this.movie = movie;
        this.from = from;
        this.to = to;
    }
}
