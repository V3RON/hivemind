package pl.aitwar.hivemind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.service.MovieService;
import reactor.core.publisher.Flux;

/**
 * Controller providing REST resource of movies
 *
 * @author Szymon Chmal
 */
@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Get all movies from database
     *
     * @return Stream of movies
     */
    @GetMapping("/movies")
    Flux<Movie> getMovies() {
        return movieService.getMovies();
    }
}
