package bookManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

import static org.junit.jupiter.api.Assertions.*;

public class SearchBooksQueryTest {

    // Black Box Test
    @Test
    void noArgsConstructor_andSetters_work() {
        SearchBooksQuery q = new SearchBooksQuery();
        assertNull(q.getTitle());
        assertNull(q.getGenre());
        assertNull(q.getAuthorName());

        q.setTitle("Clean Code");
        q.setGenre("Programming");
        q.setAuthorName("Robert C. Martin");

        assertEquals("Clean Code", q.getTitle());
        assertEquals("Programming", q.getGenre());
        assertEquals("Robert C. Martin", q.getAuthorName());
    }

    // Black Box Test
    @Test
    void allArgsConstructor_setsFields() {
        SearchBooksQuery q = new SearchBooksQuery("Domain-Driven Design", "Software", "Eric Evans");

        assertEquals("Domain-Driven Design", q.getTitle());
        assertEquals("Software", q.getGenre());
        assertEquals("Eric Evans", q.getAuthorName());
    }

    // Black Box Test
    @Test
    void equals_and_hashCode() {
        SearchBooksQuery a = new SearchBooksQuery("Title", "Genre", "Author");
        SearchBooksQuery b = new SearchBooksQuery("Title", "Genre", "Author");
        SearchBooksQuery c = new SearchBooksQuery("Other", "Genre", "Author");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    // Black Box Test
    @Test
    void toString_containsFieldValues() {
        SearchBooksQuery q = new SearchBooksQuery("Refactoring", "Programming", "Martin Fowler");
        String s = q.toString();

        assertTrue(s.contains("Refactoring"));
        assertTrue(s.contains("Programming"));
        assertTrue(s.contains("Martin Fowler"));
    }
}
