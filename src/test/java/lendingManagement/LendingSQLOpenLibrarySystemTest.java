package lendingManagement;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import pt.psoft.g1.psoftg1.PsoftG1Application;

import java.time.Duration;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sql","open"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class LendingSQLOpenLibrarySystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/lendings";

    @Test
    @Order(1)
    void testFindByLendingNumber() {
        client.get()
                .uri(BASE + "/2025/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith("application/hal+json")
                .expectBody()
                .jsonPath("$.lendingNumber").exists()
                .jsonPath("$.bookTitle").exists()
                .jsonPath("$.startDate").exists()
                .jsonPath("$.limitDate").exists()
                .jsonPath("$.daysUntilReturn").exists();
    }

    @Test
    @Order(2)
    void testSetLendingReturned() {
        client.get()
                .uri(BASE + "/avgDuration")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.lendingsAverageDuration").exists()
                .jsonPath("$.lendingsAverageDuration").isNumber();
    }

    @Test
    @Order(3)
    void testGetOverdue() {
        String body = """
        {
          "page": {
            "number": 1
          }
        }
        """;

        client.method(HttpMethod.GET)
                .uri(BASE + "/overdue")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").isNotEmpty()
                .jsonPath("$.items[0].lendingNumber").exists()
                .jsonPath("$.items[0].bookTitle").exists()
                .jsonPath("$.items[0].startDate").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}"))
                .jsonPath("$.items[0].limitDate").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}"))
                .jsonPath("$.items[0].returnedDate").isEqualTo(null)
                .jsonPath("$.items[0].daysUntilReturn").isNumber()
                .jsonPath("$.items[0].daysOverdue").isNumber()
                .jsonPath("$.items[0].fineValueInCents").isNumber();
    }
}
