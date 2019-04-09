package pl.aitwar.hivemind.service;

import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import pl.aitwar.hivemind.domain.Movie;
import pl.aitwar.hivemind.domain.OperationReport;
import pl.aitwar.hivemind.domain.enumeration.MovieState;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationReportServiceTest {
    @Autowired
    private OperationReportService operationReportService;

    @Autowired
    private ReactiveMongoOperations operations;

    @Before
    public void setUp() {
        Mono<MongoCollection<Document>> recreateCollection = operations.collectionExists(OperationReport.class)
                .flatMap(exists -> exists ? operations.dropCollection(OperationReport.class) : Mono.just(exists))
                .then(operations.createCollection(OperationReport.class, CollectionOptions.empty()
                        .size(1024 * 1024)
                        .maxDocuments(100)
                        .capped()));

        StepVerifier.create(recreateCollection).expectNextCount(1).verifyComplete();
    }

    @Test
    @Tag("slow")
    public void shouldSaveAndGenerateObjectId() {
        final Movie testMovie = new Movie(120L);
        operationReportService.saveTransitionReport(testMovie, MovieState.WAITING, MovieState.SCHEDULED).block();
        final Mono<OperationReport> report = operationReportService.findMostRecentByMovie(testMovie);

        StepVerifier
                .create(report)
                .assertNext(dbReport -> {
                    assertNotNull(dbReport.getId());
                    assertNotNull(dbReport);
                })
                .expectComplete()
                .verify();
    }
}
