package authorManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorLendingViewTest {

    // Black Box Test
    @Test
    void testAllArgsConstructorAndGetters() {
        AuthorLendingView view = new AuthorLendingView("Agatha Christie", 5L);

        assertEquals("Agatha Christie", view.getAuthorName());
        assertEquals(5L, view.getLendingCount());
    }

    // Black Box Test
    @Test
    void testNoArgsConstructorAndSetters() {
        AuthorLendingView view = new AuthorLendingView();

        view.setAuthorName("J.K. Rowling");
        view.setLendingCount(12L);

        assertEquals("J.K. Rowling", view.getAuthorName());
        assertEquals(12L, view.getLendingCount());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        AuthorLendingView v1 = new AuthorLendingView("George Orwell", 8L);
        AuthorLendingView v2 = new AuthorLendingView("George Orwell", 8L);

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentValues() {
        AuthorLendingView v1 = new AuthorLendingView("George Orwell", 8L);
        AuthorLendingView v2 = new AuthorLendingView("Aldous Huxley", 8L);

        assertNotEquals(v1, v2);
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        AuthorLendingView view = new AuthorLendingView("Arthur Conan Doyle", 3L);
        String str = view.toString();

        assertTrue(str.contains("Arthur Conan Doyle"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains("authorName"));
        assertTrue(str.contains("lendingCount"));
    }
}
