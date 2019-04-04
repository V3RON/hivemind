package pl.aitwar.hivemind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.service.MovieService;
import reactor.core.publisher.Flux;

@RestController
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    Flux<Movie> getMovies() {
        return movieService.getMovies();
    }
}
