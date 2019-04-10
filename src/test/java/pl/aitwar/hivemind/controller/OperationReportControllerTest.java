package pl.aitwar.hivemind.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.aitwar.hivemind.domain.OperationReport;
import pl.aitwar.hivemind.domain.enumeration.MovieState;
import pl.aitwar.hivemind.repository.OperationReportRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("slow")
public class OperationReportControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReactiveMongoOperations operations;

    @Autowired
    OperationReportRepository operationReportRepository;

    private List<OperationReport> testReports = List.of(
            new OperationReport(12L, MovieState.WAITING, MovieState.SCHEDULED),
            new OperationReport(144L, MovieState.SCHEDULED, MovieState.FAILED),
            new OperationReport(12L, MovieState.FAILED, MovieState.SCHEDULED)
    );

    @Before
    public void setUp() {
        Mono<MongoCollection<Document>> recreateCollection = operations.collectionExists(OperationReport.class)
                .flatMap(exists -> exists ? operations.dropCollection(OperationReport.class) : Mono.just(exists))
                .then(operations.createCollection(OperationReport.class, CollectionOptions.empty()
                        .size(1024 * 1024)
                        .maxDocuments(100)
                        .capped()));

        StepVerifier.create(recreateCollection).expectNextCount(1).verifyComplete();
        StepVerifier.create(operations.insertAll(testReports)).expectNextCount(testReports.size()).verifyComplete();
    }

    @Test
    public void getReportsTest() {
        webTestClient.get().uri("/reports")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(OperationReport.class)
                .returnResult().getResponseBody().equals(testReports);
    }

    @Test
    public void getReportsByMovieTest() {
        webTestClient.get().uri("/reports").attribute("movie", 12L)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(OperationReport.class)
                .returnResult().getResponseBody().equals(
                testReports.stream().filter(report -> report.getMovie().equals(12L)).collect(Collectors.toList())
        );
    }

}
