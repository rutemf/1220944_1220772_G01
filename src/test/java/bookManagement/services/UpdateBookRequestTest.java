package bookManagement.services;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UpdateBookRequestTest {

    // Black Box Test
    @Test
    void testNoArgsConstructor_SettersAndGetters() {
        UpdateBookRequest r = new UpdateBookRequest();

        r.setIsbn("978-1234567890");
        r.setTitle("Clean Code");
        r.setGenre("Programming");
        r.setDescription("A handbook of agile software craftsmanship");
        r.setAuthors(Arrays.asList(1L, 2L));

        Genre genreObj = mock(Genre.class);
        r.setGenreObj(genreObj);

        MultipartFile photo = mock(MultipartFile.class);
        r.setPhoto(photo);
        r.setPhotoURI("https://cdn/books/cc.jpg");

        Author a1 = mock(Author.class);
        Author a2 = mock(Author.class);
        r.setAuthorObjList(Arrays.asList(a1, a2));

        assertEquals("978-1234567890", r.getIsbn());
        assertEquals("Clean Code", r.getTitle());
        assertEquals("Programming", r.getGenre());
        assertEquals("A handbook of agile software craftsmanship", r.getDescription());
        assertEquals(List.of(1L, 2L), r.getAuthors());
        assertSame(genreObj, r.getGenreObj());
        assertSame(photo, r.getPhoto());
        assertEquals("https://cdn/books/cc.jpg", r.getPhotoURI());
        assertEquals(List.of(a1, a2), r.getAuthorObjList());
    }

    // Black Box Test
    @Test
    void testCustomConstructor_SetsCoreFields() {
        UpdateBookRequest r = new UpdateBookRequest(
                "978-0132350884",
                "Clean Code",
                "Programming",
                Arrays.asList(10L, 20L),
                "Must-read"
        );

        assertEquals("978-0132350884", r.getIsbn());
        assertEquals("Clean Code", r.getTitle());
        assertEquals("Programming", r.getGenre());
        assertEquals("Must-read", r.getDescription());
        assertEquals(List.of(10L, 20L), r.getAuthors());

        assertNull(r.getPhoto());
        assertNull(r.getPhotoURI());
        assertNull(r.getGenreObj());
        assertNull(r.getAuthorObjList());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode_Simple() {
        UpdateBookRequest a = new UpdateBookRequest(
                "ISBN-1",
                "Title",
                "Genre",
                Arrays.asList(1L, 2L),
                "Desc"
        );
        a.setPhoto(null);
        a.setPhotoURI(null);
        a.setGenreObj(null);
        a.setAuthorObjList(null);

        UpdateBookRequest b = new UpdateBookRequest(
                "ISBN-1",
                "Title",
                "Genre",
                Arrays.asList(1L, 2L),
                "Desc"
        );
        b.setPhoto(null);
        b.setPhotoURI(null);
        b.setGenreObj(null);
        b.setAuthorObjList(null);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        UpdateBookRequest c = new UpdateBookRequest(
                "ISBN-2",
                "Title",
                "Genre",
                Arrays.asList(1L, 2L),
                "Desc"
        );
        assertNotEquals(a, c);
    }
}
