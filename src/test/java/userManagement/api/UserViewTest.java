package userManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.api.UserView;

import static org.junit.jupiter.api.Assertions.*;

public class UserViewTest {

    private static final String SAMPLE_ID = "AAAAA#sr";
    private static final String SAMPLE_USERNAME = "user@example.com";
    private static final String SAMPLE_FULLNAME = "Joao Silva";

    @Test
    void testNoArgsConstructor() {
        UserView view = new UserView();
        assertNotNull(view, "O construtor vazio não deve retornar nulo.");
        assertNull(view.getId(), "O ID deve ser nulo após o construtor vazio.");
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        UserView view = new UserView(SAMPLE_ID, SAMPLE_USERNAME, SAMPLE_FULLNAME);

        assertNotNull(view);
        assertEquals(SAMPLE_ID, view.getId(), "O ID deve corresponder ao valor passado.");
        assertEquals(SAMPLE_USERNAME, view.getUsername(), "O username deve corresponder ao valor passado.");
        assertEquals(SAMPLE_FULLNAME, view.getFullName(), "O nome completo deve corresponder ao valor passado.");
    }

    @Test
    void testSetters() {
        UserView view = new UserView();
        view.setId("newId");
        view.setUsername("newuser@mail.com");
        view.setFullName("Novo Nome");

        assertEquals("newId", view.getId());
        assertEquals("newuser@mail.com", view.getUsername());
        assertEquals("Novo Nome", view.getFullName());
    }

    @Test
    void testEqualityAndHashCodeSuccess() {
        UserView view1 = new UserView(SAMPLE_ID, SAMPLE_USERNAME, SAMPLE_FULLNAME);
        UserView view2 = new UserView(SAMPLE_ID, SAMPLE_USERNAME, SAMPLE_FULLNAME);

        assertEquals(view1, view2, "Objetos com o mesmo conteúdo devem ser considerados iguais.");
        assertEquals(view1.hashCode(), view2.hashCode(), "Hash codes devem ser iguais para objetos iguais.");
    }

    @Test
    void testEqualityAndHashCodeDifference() {
        UserView view1 = new UserView(SAMPLE_ID, SAMPLE_USERNAME, SAMPLE_FULLNAME);
        UserView view2 = new UserView("different-id", SAMPLE_USERNAME, SAMPLE_FULLNAME);

        assertNotEquals(view1, view2, "Objetos com IDs diferentes não devem ser iguais.");
        assertNotEquals(view1.hashCode(), view2.hashCode(), "Hash codes devem ser diferentes.");
    }

    @Test
    void testEqualsEdgeCases() {
        UserView view = new UserView(SAMPLE_ID, SAMPLE_USERNAME, SAMPLE_FULLNAME);

        assertNotEquals(null, view, "O objeto não deve ser igual a nulo.");
    }
}
