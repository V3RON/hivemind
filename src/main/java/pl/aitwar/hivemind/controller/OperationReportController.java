package pl.aitwar.hivemind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.domain.OperationReport;
import pl.aitwar.hivemind.service.OperationReportService;
import reactor.core.publisher.Flux;

import java.util.Optional;

/**
 * Controller providing resource of reports
 *
 * @author Szymon Chmal
 */
@RestController
@RequestMapping("reports")
public class OperationReportController {
    private final OperationReportService operationReportService;

    public OperationReportController(OperationReportService operationReportService) {
        this.operationReportService = operationReportService;
    }

    /**
     * Get all operations from database
     *
     * @param movieId Optional ID of Movie to filter reports
     * @return Stream of movies
     */
    @GetMapping
    Flux<OperationReport> getReports(@RequestParam(value = "movie", required = false) Optional<Long> movieId) {
        return movieId
                .map(Movie::new)
                .map(operationReportService::findByMovie)
                .orElseGet(operationReportService::findRecent);
    }
}
