package genreManagement;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import pt.psoft.g1.psoftg1.PsoftG1Application;

import java.time.Duration;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sql","open"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class GenreSQLSystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/genres";

    @Test
    @Order(1)
    void testGetTop5Genres() {
        client.get()
                .uri(BASE + "/top5")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(allOf(greaterThan(0), lessThanOrEqualTo(5)))
                .jsonPath("$.items[*].genreView.genre").value(everyItem(not(isEmptyOrNullString())))
                .jsonPath("$.items[*].bookCount").value(everyItem(greaterThanOrEqualTo(0)));
    }

    @Test
    @Order(2)
    void testGetLendingsPerMonthLastTwelveMonths() {
        client.get()
                .uri(BASE + "/lendingsPerMonthLastTwelveMonths")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(greaterThan(0))
                .jsonPath("$.items[0].year").isNumber()
                .jsonPath("$.items[0].month").value(allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(12)))
                .jsonPath("$.items[0].lendingsCount").isArray()
                .jsonPath("$.items[0].lendingsCount.length()").value(greaterThan(0))
                .jsonPath("$.items[0].lendingsCount[*].genre").value(everyItem(not(isEmptyOrNullString())))
                .jsonPath("$.items[0].lendingsCount[*].value").value(everyItem(greaterThanOrEqualTo(0)));
    }

    @Test
    @Order(3)
    void testGetLendingsAverageDurationPerMonth() {
        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE + "/lendingsAverageDurationPerMonth")
                        .queryParam("startDate", "2024-01-01")
                        .queryParam("endDate",   "2025-12-31")
                        .build())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(greaterThan(0))
                .jsonPath("$.items[0].year").isNumber()
                .jsonPath("$.items[0].month").isNumber()
                .jsonPath("$.items[0].durationAverages").isArray()
                .jsonPath("$.items[0].durationAverages[*].genre").value(everyItem(not(isEmptyOrNullString())))
                .jsonPath("$.items[0].durationAverages[*].value").value(everyItem(greaterThanOrEqualTo(0.0)));
    }

    @Test
    @Order(4)
    void testGetAverageLendings() {
        String body = """
        {
          "page": {
            "number": 1,
            "size": 10
          },
          "query": {
            "year": 2025,
            "month": 10
          }
        }
        """;

        client.post()
                .uri(BASE + "/avgLendingsPerGenre")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(allOf(greaterThan(0), lessThanOrEqualTo(5)))
                .jsonPath("$.items[*].genre").value(everyItem(not(isEmptyOrNullString())))
                .jsonPath("$.items[*].value").value(everyItem(greaterThanOrEqualTo(0)));
    }
}
