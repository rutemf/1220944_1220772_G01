package authorManagement;

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
import pt.psoft.g1.psoftg1.LibraryManagementApplication;

import java.time.Duration;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = LibraryManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"nosql","google"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class AuthorNoSQLSystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/authors";

    @Test
    @Order(1)
    void testFindByAuthorNumber() {
        client.get()
                .uri(BASE + "/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.authorNumber").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("JK Rowling")
                .jsonPath("$.bio").isEqualTo("British author best known for the Harry Potter series.")
                .jsonPath("$.photo").value(nullValue())
                .jsonPath("$._links.author").value(endsWith("/api/authors/1"))
                .jsonPath("$._links.booksByAuthor").value(endsWith("/api/authors/1/books"))
                .jsonPath("$._links.photo").value(endsWith("/api/authors/1/photo"));
    }

    @Test
    @Order(2)
    void testFindByName() {
        client.get()
                .uri(BASE + "?name=JK Rowling")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(allOf(greaterThan(0)))
                .jsonPath("$.items[0].authorNumber").isEqualTo(1)
                .jsonPath("$.items[0].name").isEqualTo("JK Rowling")
                .jsonPath("$.items[0].bio").isEqualTo("British author best known for the Harry Potter series.")
                .jsonPath("$.items[0].photo").value(nullValue())
                .jsonPath("$.items[0]._links.author").value(endsWith("/api/authors/1"))
                .jsonPath("$.items[0]._links.booksByAuthor").value(endsWith("/api/authors/1/books"))
                .jsonPath("$.items[0]._links.photo").value(endsWith("/api/authors/1/photo"));
    }
}
