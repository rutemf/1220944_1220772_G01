package bookManagement;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import pt.psoft.g1.psoftg1.PsoftG1Application;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sql","open"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class BookSQLSystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/books";

    @Test
    @Order(1)
    void testFindByIsbn() {
        client.get()
                .uri(BASE + "/9789723716160")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.title").isEqualTo("Como se Desenha Uma Casa")
                .jsonPath("$.genre").isEqualTo("Mystery")
                .jsonPath("$.description").isEqualTo("Como quem, vindo de países distantes fora do caminho.")
                .jsonPath("$.isbn").isEqualTo("9789723716160");
    }

    @Test
    @Order(2)
    void testFindExternal() {
        client.get()
                .uri(BASE + "/external?title=Art of War")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith("application/json")
                .expectBody(String.class)
                .value(body -> assertEquals("9781403774644", body));
    }

    @Test
    @Order(3)
    void testTop5BooksLent() {
        client.get()
                .uri(BASE + "/top5")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(len -> assertTrue((int) len <= 5))
                .jsonPath("$.items[0].bookView").exists()
                .jsonPath("$.items[0].bookView.title").isNotEmpty()
                .jsonPath("$.items[0].bookView.genre").isNotEmpty()
                .jsonPath("$.items[0].bookView.isbn").isNotEmpty()
                .jsonPath("$.items[0].lendingCount").isNumber()
                .jsonPath("$.items[0].bookView._links.self").isNotEmpty()
                .jsonPath("$.items[0].bookView._links.photo").isNotEmpty()
                .jsonPath("$.items[0].bookView._links.authors").isArray();
    }
}
