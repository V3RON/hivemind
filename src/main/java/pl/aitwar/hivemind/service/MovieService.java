package pl.aitwar.hivemind.service;

import org.springframework.stereotype.Service;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.domain.enumeration.MovieState;
import pl.aitwar.hivemind.repository.MovieRepository;
import reactor.core.publisher.Flux;

/**
 * Manage movies being held in local database
 *
 * @author Szymon Chmal
 */
@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Flux<Movie> getMoviesByState(MovieState movieState) {
        return movieRepository.findByStateEquals(movieState);
    }
}
