package bookManagement.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreSQL;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookSQLTest {
    
    @Mock GenreSQL genreSQL;
    @Mock Genre domainGenre;
    @Mock AuthorSQL authorSQL1;
    @Mock AuthorSQL authorSQL2;
    @Mock Author domainAuthor1;
    @Mock Author domainAuthor2;

    // New Reflection of BookSQL
    private static BookSQL newBookSQL() {
        try {
            Constructor<BookSQL> ctor = BookSQL.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate BookSQL via reflection", e);
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

        BookSQL sql = BookSQL.fromDomain(domain);

        assertNotNull(sql.getId(), "ID deve ser gerado");
        assertEquals(isbn, sql.getIsbn().toString());
        assertEquals(title, sql.getTitle());
        assertEquals(desc, sql.getDescription());
        assertNull(sql.getGenre(), "Genre deve ser null quando domínio tem null");
        assertNotNull(sql.getAuthors(), "Lista de authors não deve ser null");
        assertTrue(sql.getAuthors().isEmpty(), "Lista de authors deve estar vazia");
        assertEquals(photo, sql.getPhotoURI());
    }

    // Black Box Test
    @Test
    void toDomain_roundTrip_withNullsAndEmptyAuthors() {
        String isbn = "0306406152";
        Book domain = new Book(isbn, "Refactoring", "Desc", null, List.of(), null);

        BookSQL sql = BookSQL.fromDomain(domain);
        Book back = sql.toDomain();

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
        BookSQL empty = newBookSQL();
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
    void toDomain_mapsGenreAndAuthors_usingProvidedSqlObjects() {
        BookSQL sql = newBookSQL();
        sql.setId("BOOK#1");
        sql.setIsbn(new Isbn("9780306406157"));
        sql.setTitle("Domain-Driven Design");
        sql.setDescription("Blue book");
        sql.setPhotoURI("photo://uri");

        // GenreSQL -> Genre (mock)
        when(genreSQL.toDomain()).thenReturn(domainGenre);
        sql.setGenre(genreSQL);

        // AuthorSQL -> Author (mocks)
        when(authorSQL1.toDomain()).thenReturn(domainAuthor1);
        when(authorSQL2.toDomain()).thenReturn(domainAuthor2);
        sql.setAuthors(List.of(authorSQL1, authorSQL2));

        Book back = sql.toDomain();

        assertEquals("9780306406157", back.getIsbn().toString());
        assertEquals("Domain-Driven Design", back.getTitle().toString());
        assertEquals("Blue book", back.getDescription().toString());
        assertSame(domainGenre, back.getGenre(), "Deve usar GenreSQL.toDomain()");
        assertNotNull(back.getAuthors());
        assertEquals(2, back.getAuthors().size());
        assertSame(domainAuthor1, back.getAuthors().get(0));
        assertSame(domainAuthor2, back.getAuthors().get(1));
        assertEquals("photo://uri", back.getPhotoURI());

        verify(genreSQL, times(1)).toDomain();
        verify(authorSQL1, times(1)).toDomain();
        verify(authorSQL2, times(1)).toDomain();
    }

    // White Box Test
    @Test
    void toDomain_handlesNullAuthorsListAsNull_andNonNullAsMappedList() {
        BookSQL sqlNullAuthors = newBookSQL();
        sqlNullAuthors.setIsbn(new Isbn("9780306406157"));
        sqlNullAuthors.setTitle("T1");
        sqlNullAuthors.setDescription("D1");
        sqlNullAuthors.setAuthors(null);
        Book b1 = sqlNullAuthors.toDomain();
        assertNull(b1.getAuthors(), "Quando authors é null no SQL, domínio deve receber null");

        BookSQL sqlEmptyAuthors = newBookSQL();
        sqlEmptyAuthors.setIsbn(new Isbn("9780306406157"));
        sqlEmptyAuthors.setTitle("T2");
        sqlEmptyAuthors.setDescription("D2");
        sqlEmptyAuthors.setAuthors(List.of());
        Book b2 = sqlEmptyAuthors.toDomain();
        assertNotNull(b2.getAuthors());
        assertTrue(b2.getAuthors().isEmpty());
    }
}