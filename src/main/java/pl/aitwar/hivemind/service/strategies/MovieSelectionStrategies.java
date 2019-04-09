package pl.aitwar.hivemind.service.strategies;

import pl.aitwar.hivemind.domain.enumeration.MovieState;

import java.util.function.Function;

/**
 * A collection of movie selection strategies classes.
 * Each class describe the way how the chunks are being generated (which movies
 * are taken into consideration - failed or only waiting ones)
 *
 * @author Szymon Chmal
 */
public class MovieSelectionStrategies {

    /**
     * Basic strategy trying to accomplish 100% success rate
     * by retrying to scrap movies over and over
     */
    public final static MovieSelectionStrategy REPEAT_THEN_PROCEED = (chunkSize, movieService) ->
            movieService.getMoviesByState(MovieState.FAILED)
                    .concatWith(movieService.getMoviesByState(MovieState.WAITING))
                    .take(chunkSize);

    /**
     * Strategy ignoring all failed operations
     * focused on getting new entries
     */
    public final static MovieSelectionStrategy PROCEED_AT_ANY_COST = (chunkSize, movieService) ->
            movieService.getMoviesByState(MovieState.WAITING)
                    .concatWith(movieService.getMoviesByState(MovieState.FAILED))
                    .take(chunkSize);

    /**
     * Strategy trying to vary the actions taken (repeat and proceed)
     */
    public final static Function<Integer, MovieSelectionStrategy> INTERACTIVE = nice -> (chunkSize, movieService) -> {
        Integer failedCount = chunkSize * nice / 10;

        return movieService.getMoviesByState(MovieState.FAILED)
                .take(failedCount)
                .concatWith(movieService.getMoviesByState(MovieState.WAITING))
                .take(chunkSize - failedCount);
    };
}
