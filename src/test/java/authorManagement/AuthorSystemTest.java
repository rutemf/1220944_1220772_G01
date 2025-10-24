package authorManagement;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pt.psoft.g1.psoftg1.PsoftG1Application;

import java.time.Duration;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.everyItem;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sql","open"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorSystemTest {

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

    @Test
    @Order(3)
    void testGetBooksByAuthorNumber() {
        client.get()
                .uri(BASE + "/4/books")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(allOf(greaterThan(0)))
                .jsonPath("$.items[*].title").value(everyItem(allOf(notNullValue(), instanceOf(String.class))))
                .jsonPath("$.items[*].authors[*]").value(hasItem("Agatha Christie"))
                .jsonPath("$.items[*].genre").value(everyItem(allOf(notNullValue(), instanceOf(String.class))))
                .jsonPath("$.items[*].description").value(everyItem(allOf(notNullValue(), instanceOf(String.class))))
                .jsonPath("$.items[*].isbn").value(everyItem(matchesPattern("\\d{13}")));
    }

    @Test
    @Order(4)
    void testTop5() {
        client.get()
                .uri(BASE + "/top5")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items.length()").value(allOf(greaterThan(0), lessThanOrEqualTo(5)))
                .jsonPath("$.items[*].authorName").value(everyItem(allOf(notNullValue(), instanceOf(String.class))))
                .jsonPath("$.items[*].lendingCount").value(everyItem(allOf(notNullValue(), instanceOf(Integer.class))));
    }

    @Test
    @Order(5)
    void testCreate() {
        client.post()
                .uri(BASE) // ex: /api/authors
                .contentType(MULTIPART_FORM_DATA)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData("name", "Test Author B")
                .with("bio", "British author best known for tests."))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Author B")
                .jsonPath("$.bio").value(containsString("British author"))
                .jsonPath("$.photo").value(nullValue());
    }

    @Test
    @Order(6)
    void testGetSpecificAuthorPhoto() {
        client.get()
                .uri(BASE + "/1/photo")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(7)
    void testGetCoAuthors() {
        client.get()
                .uri(BASE + "/4/coauthors")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.author.name").isEqualTo("Agatha Christie")
                .jsonPath("$.author.bio").value(not(isEmptyOrNullString()))
                .jsonPath("$.author._links.author").value(containsString("/api/authors/4"))
                .jsonPath("$.coauthors").isArray()
                .jsonPath("$.coauthors.length()").value(greaterThan(0))
                .jsonPath("$.coauthors[*].name").value(everyItem(allOf(notNullValue(), instanceOf(String.class))))
                .jsonPath("$.coauthors[*].books").isArray()
                .jsonPath("$.coauthors[*].books[*].title").value(everyItem(allOf(notNullValue(), instanceOf(String.class))))
                .jsonPath("$.coauthors[*].books[*].isbn").value(everyItem(matchesPattern("\\d{13}")));
    }
}
