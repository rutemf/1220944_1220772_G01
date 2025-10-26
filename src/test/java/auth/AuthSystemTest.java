package auth;

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
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sql","open"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AuthSystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/public";

    @Test
    @Order(1)
    void testRegister() {
        String uniqueEmail = "tester" + System.currentTimeMillis() + "@gmail.com";

        var requestBody = Map.of(
                "name", "Test",
                "username", uniqueEmail,
                "password", "Test04Mickel@"
        );

        client.post()
                .uri(BASE + "/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.username").isEqualTo(uniqueEmail)
                .jsonPath("$.fullName").isEqualTo("Test")
                .jsonPath("$.id").doesNotExist();
    }
}
