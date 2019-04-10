package pl.aitwar.hivemind.service;

import org.springframework.stereotype.Service;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.domain.OperationReport;
import pl.aitwar.hivemind.domain.enumeration.MovieState;
import pl.aitwar.hivemind.repository.OperationReportRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service responsible for creating and manipulating operation reports
 *
 * @author Szymon Chmal
 */
@Service
public class OperationReportService {
    private final OperationReportRepository operationReportRepository;

    public OperationReportService(OperationReportRepository operationReportRepository) {
        this.operationReportRepository = operationReportRepository;
    }

    public Flux<OperationReport> findRecent() {
        return operationReportRepository.findAllByOrderByDateDesc();
    }

    public Mono<OperationReport> findMostRecentByMovie(Movie m) {
        return operationReportRepository.findFirstByMovieOrderByDateDesc(m.getId());
    }

    public Mono<OperationReport> saveTransitionReport(Movie m, MovieState from, MovieState to) {
        OperationReport report = new OperationReport(m.getId(), from, to);
        return operationReportRepository.save(report);
    }

    public Flux<OperationReport> findByMovie(Movie movie) {
        return operationReportRepository.findAllByMovie(movie.getId());
    }
}
