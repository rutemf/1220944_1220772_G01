package readerManagement;

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

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(classes = PsoftG1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sql","open"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class ReaderSQLSystemTest {

    @LocalServerPort
    int port;

    private WebTestClient client;

    @PostConstruct
    void initClient() {
        this.client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofSeconds(10)).build();
    }

    private static final String BASE = "/api/readers";

    @Test
    @Order(1)
    void testFindByPhoneNumber() {
        client.get()
                .uri(BASE + "?phoneNumber=910663221")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items[0].readerNumber").exists()
                .jsonPath("$.items[0].birthDate").exists()
                .jsonPath("$.items[0].phoneNumber").exists()
                .jsonPath("$.items[0].gdprConsent").exists()
                .jsonPath("$.items[0].marketingConsent").exists()
                .jsonPath("$.items[0].thirdPartySharingConsent").exists()
                .jsonPath("$.items[0].interestList").isArray();
    }

    @Test
    @Order(2)
    void testFindByReaderName() {
        client.get()
                .uri(BASE + "?name=Miguel Cardoso")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items[0].readerNumber").exists()
                .jsonPath("$.items[0].birthDate").exists()
                .jsonPath("$.items[0].phoneNumber").exists()
                .jsonPath("$.items[0].gdprConsent").exists()
                .jsonPath("$.items[0].marketingConsent").exists()
                .jsonPath("$.items[0].thirdPartySharingConsent").exists()
                .jsonPath("$.items[0].interestList").isArray();
    }

    @Test
    @Order(3)
    void testFindByReaderNumber() {
        client.get()
                .uri(BASE + "/2025/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.readerNumber").isEqualTo("2025/1")
                .jsonPath("$.email").isEqualTo("miguel@gmail.com")
                .jsonPath("$.fullName").isEqualTo("Miguel Cardoso")
                .jsonPath("$.birthDate").isEqualTo("2004-07-04")
                .jsonPath("$.phoneNumber").isEqualTo("910663221")
                .jsonPath("$.gdprConsent").isEqualTo(true)
                .jsonPath("$.marketingConsent").isEqualTo(true)
                .jsonPath("$.thirdPartySharingConsent").isEqualTo(true)
                .jsonPath("$.interestList[0]").isEqualTo("Romance")
                .jsonPath("$.interestList[1]").isEqualTo("Mystery")
                .jsonPath("$.interestList[2]").isEqualTo("Fantasy");
    }

    @Test
    @Order(4)
    void testGetTop() {
        client.get()
                .uri(BASE + "/top5")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.items").isArray()
                .jsonPath("$.items[0].readerNumber").exists()
                .jsonPath("$.items[0].birthDate").exists()
                .jsonPath("$.items[0].phoneNumber").exists()
                .jsonPath("$.items[0].gdprConsent").exists()
                .jsonPath("$.items[0].marketingConsent").exists()
                .jsonPath("$.items[0].thirdPartySharingConsent").exists()
                .jsonPath("$.items[0].interestList").isArray();
    }
}
