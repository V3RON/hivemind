package pl.aitwar.hivemind.service.strategies;

import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.service.MovieService;
import reactor.core.publisher.Flux;

/**
 * Interface marking movie selecting strategies
 *
 * @author Szymon Chmal
 */

@FunctionalInterface
public interface MovieSelectionStrategy {
    Flux<Movie> getMovieChunk(Integer chunkSize, MovieService movieService);
}
