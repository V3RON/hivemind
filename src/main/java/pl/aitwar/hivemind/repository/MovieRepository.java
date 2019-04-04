package pl.aitwar.hivemind.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.domain.MovieState;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveCrudRepository<Movie, Long> {
    Flux<Movie> findByStateEquals(MovieState state);
}
