package pt.psoft.g1.psoftg1.authormanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorTest {
    private final String validName = "Tiago Alberto";
    private final String validBio = "O Tiago Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.";

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


}