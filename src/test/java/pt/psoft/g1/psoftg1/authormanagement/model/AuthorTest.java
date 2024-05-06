package pt.psoft.g1.psoftg1.authormanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    private final String validName = "João Alberto";
    private final String validBio = "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.";

    @BeforeEach
    void setUp() {

    }

    @Test
    void ensureNameNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Author(null,validBio));
    }

    @Test
    void ensureBioNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Author(validName,null));
    }

    @Test
    void whenVersionIsStaleItIsNotPossibleToPatch() {
        final var subject = new Author(validName,validBio);

        assertThrows(StaleObjectStateException.class, () -> subject.applyPatch(999, validName, validName));
    }

    @Test
    void testCreateAuthorRequest() {
        final CreateAuthorRequest request = new CreateAuthorRequest(validBio, validName);
        assertNotNull(request);
    }

    @Test
    void testUpdateAuthorRequest() {
        final UpdateAuthorRequest request = new UpdateAuthorRequest(validBio, validName);
        assertNotNull(request);
    }

}