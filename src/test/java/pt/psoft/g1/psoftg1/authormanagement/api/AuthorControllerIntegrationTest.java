package pt.psoft.g1.psoftg1.authormanagement.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Based on https://www.baeldung.com/spring-boot-testing
 * <p>Adaptations to Junit 5 with ChatGPT
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService service;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //TODO: fix this test class
/*
    @Test
    public void givenAuthors_whenGetAuthors_thenReturnJsonArray()
            throws Exception {

        Author alex = new Author("Alex", "O Alex escreveu livros");

        List<Author> allAuthors = List.of(alex);

        given(service.findAll()).willReturn(allAuthors);

        mvc.perform(get("/api/authors?name=Alex")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
*/
}
