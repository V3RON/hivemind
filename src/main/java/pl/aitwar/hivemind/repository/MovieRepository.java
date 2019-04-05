package pl.aitwar.hivemind.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.domain.enumeration.MovieState;
import reactor.core.publisher.Flux;

/**
 * Repository defining operations on movie collection
 *
 * @author Szymon Chmal
 */
public interface MovieRepository extends ReactiveCrudRepository<Movie, Long> {
    Flux<Movie> findByStateEquals(MovieState state);
}
