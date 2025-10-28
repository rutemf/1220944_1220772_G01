package bookManagement.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorNoSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookNoSQL;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class BookNoSQLTest {

    @Mock GenreNoSQL genreNoSQL;
    @Mock Genre domainGenre;
    @Mock AuthorNoSQL authorNoSQL1;
    @Mock AuthorNoSQL authorNoSQL2;
    @Mock Author domainAuthor1;
    @Mock Author domainAuthor2;

    // New Reflection of BookSQL
    private static BookNoSQL newBookNoSQL() {
        try {
            Constructor<BookNoSQL> ctor = BookNoSQL.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate BookNoSQL via reflection", e);
        }
    }

    // Black Box Test
    @Test
    void fromDomain_mapsBasicFields_withNullGenreAndEmptyAuthors() {
        String isbn = "9780306406157";
        String title = "Clean Code";
        String desc = "A handbook of Agile software craftsmanship";
        String photo = "https://cdn.example.com/clean-code.jpg";

        Book domain = new Book(isbn, title, desc, null, List.of(), photo);

        BookNoSQL noSQL = BookNoSQL.fromDomain(domain);

        assertNotNull(noSQL.getId(), "ID deve ser gerado");
        assertEquals(isbn, noSQL.getIsbn());
        assertEquals(title, noSQL.getTitle());
        assertEquals(desc, noSQL.getDescription());
        assertNull(noSQL.getGenre(), "Genre deve ser null quando domínio tem null");
        assertNotNull(noSQL.getAuthors(), "Lista de authors não deve ser null");
        assertTrue(noSQL.getAuthors().isEmpty(), "Lista de authors deve estar vazia");
        assertEquals(photo, noSQL.getPhotoURI());
    }

    // Black Box Test
    @Test
    void toDomain_roundTrip_withNullsAndEmptyAuthors() {
        String isbn = "0306406152";
        Book domain = new Book(isbn, "Refactoring", "Desc", null, List.of(), null);

        BookNoSQL noSQL = BookNoSQL.fromDomain(domain);
        Book back = noSQL.toDomain();

        assertEquals(domain.getIsbn().toString(), back.getIsbn().toString());
        assertEquals(domain.getTitle().toString(), back.getTitle().toString());
        assertEquals(domain.getDescription().toString(), back.getDescription().toString());
        assertNull(back.getGenre());
        assertNotNull(back.getAuthors());
        assertTrue(back.getAuthors().isEmpty());
        assertNull(back.getPhotoURI());
    }

    // White Box Test
    @Test
    void jpaNoArgsConstructor_defaultsAreNull() {
        BookNoSQL empty = newBookNoSQL();
        assertNull(empty.getId());
        assertNull(empty.getIsbn());
        assertNull(empty.getTitle());
        assertNull(empty.getDescription());
        assertNull(empty.getGenre());
        assertNull(empty.getAuthors());
        assertNull(empty.getPhotoURI());
    }

    // White Box Test
    @Test
    void toDomain_mapsGenreAndAuthors_usingProvidedNoSqlObjects() {
        BookNoSQL noSQL = newBookNoSQL();
        noSQL.setId("BOOK#1");
        noSQL.setIsbn("9780306406157");
        noSQL.setTitle("Domain-Driven Design");
        noSQL.setDescription("Blue book");
        noSQL.setPhotoURI("photo://uri");

        // GenreNoSQL -> Genre (mock)
        when(genreNoSQL.toDomain()).thenReturn(domainGenre);
        noSQL.setGenre(genreNoSQL);

        // AuthorNOSQL -> Author (mocks)
        when(authorNoSQL1.toDomain()).thenReturn(domainAuthor1);
        when(authorNoSQL2.toDomain()).thenReturn(domainAuthor2);
        noSQL.setAuthors(List.of(authorNoSQL1, authorNoSQL2));

        Book back = noSQL.toDomain();

        assertEquals("9780306406157", back.getIsbn().toString());
        assertEquals("Domain-Driven Design", back.getTitle().toString());
        assertEquals("Blue book", back.getDescription().toString());
        assertSame(domainGenre, back.getGenre(), "Deve usar GenreNoSQL.toDomain()");
        assertNotNull(back.getAuthors());
        assertEquals(2, back.getAuthors().size());
        assertSame(domainAuthor1, back.getAuthors().get(0));
        assertSame(domainAuthor2, back.getAuthors().get(1));
        assertEquals("photo://uri", back.getPhotoURI());

        verify(genreNoSQL, times(1)).toDomain();
        verify(authorNoSQL1, times(1)).toDomain();
        verify(authorNoSQL2, times(1)).toDomain();
    }

    // White Box Test
    @Test
    void toDomain_handlesNullAuthorsListAsNull_andNonNullAsMappedList() {
        BookNoSQL noSqlNullAuthors = newBookNoSQL();
        noSqlNullAuthors.setIsbn("9780306406157");
        noSqlNullAuthors.setTitle("T1");
        noSqlNullAuthors.setDescription("D1");
        noSqlNullAuthors.setAuthors(null);
        Book b1 = noSqlNullAuthors.toDomain();
        assertNull(b1.getAuthors(), "Quando authors é null no NoSQL, domínio deve receber null");

        BookNoSQL noSqlEmptyAuthors = newBookNoSQL();
        noSqlEmptyAuthors.setIsbn("9780306406157");
        noSqlEmptyAuthors.setTitle("T2");
        noSqlEmptyAuthors.setDescription("D2");
        noSqlEmptyAuthors.setAuthors(List.of());
        Book b2 = noSqlEmptyAuthors.toDomain();
        assertNotNull(b2.getAuthors());
        assertTrue(b2.getAuthors().isEmpty());
    }
}
