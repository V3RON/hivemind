package pl.aitwar.hivemind.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.aitwar.hivemind.domain.OperationReport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository defining operations on operation report collection
 *
 * @author Szymon Chmal
 */
public interface OperationReportRepository extends ReactiveCrudRepository<OperationReport, ObjectId> {
    Mono<OperationReport> findFirstByMovieOrderByDateDesc(Long movieId);

    Flux<OperationReport> findAllByOrderByDateDesc();

    Flux<OperationReport> findAllByMovie(Long m);
}
