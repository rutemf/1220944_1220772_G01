package userManagement;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import pt.psoft.g1.psoftg1.PsoftG1Application;

import java.time.Duration;
import java.util.UUID;

import static org.hamcrest.Matchers.matchesPattern;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"nosql","google"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserNoSQLSystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/admin/users";

    @Test
    @Order(1)
    void testCreate() {
        String email = "user+" + UUID.randomUUID() + "@example.com";
        String body = """
        {
          "username": "%s",
          "password": "Secret123!",
          "name": "Alice Example",
          "role": "ADMIN"
        }
        """.formatted(email);

        client.post()
                .uri(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith("application/json")
                .expectBody()
                .jsonPath("$.username").value(matchesPattern("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
                .jsonPath("$.fullName").isEqualTo("Alice Example");
    }
}
