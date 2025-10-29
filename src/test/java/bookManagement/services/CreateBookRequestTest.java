package bookManagement.services;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CreateBookRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        CreateBookRequest request = new CreateBookRequest();

        request.setDescription("A mystery novel set in London.");
        request.setPhotoURI("https://example.com/photo.jpg");
        request.setPhoto(mock(MultipartFile.class));

        assertEquals("A mystery novel set in London.", request.getDescription());
        assertEquals("https://example.com/photo.jpg", request.getPhotoURI());
        assertNotNull(request.getPhoto());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateBookRequest req1 = new CreateBookRequest();
        CreateBookRequest req2 = new CreateBookRequest();

        req1.setDescription("desc");
        req2.setDescription("desc");

        req1.setTitle("Title");
        req2.setTitle("Title");

        req1.setGenre("Drama");
        req2.setGenre("Drama");

        req1.setAuthors(List.of(1L, 2L));
        req2.setAuthors(List.of(1L, 2L));

        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    void testNotEqualsDifferentFields() {
        CreateBookRequest req1 = new CreateBookRequest();
        CreateBookRequest req2 = new CreateBookRequest();

        req1.setTitle("Book A");
        req2.setTitle("Book B");

        req1.setGenre("Fiction");
        req2.setGenre("Fiction");

        req1.setAuthors(List.of(1L));
        req2.setAuthors(List.of(1L));

        assertNotEquals(req1, req2);
    }

    @Test
    void testToStringContainsImportantFields() {
        CreateBookRequest req = new CreateBookRequest();
        req.setTitle("The Hobbit");
        req.setGenre("Fantasy");
        req.setDescription("Classic adventure story");
        req.setAuthors(List.of(10L, 20L));

        String str = req.toString();

        assertTrue(str.contains("The Hobbit"));
        assertTrue(str.contains("Fantasy"));
        assertTrue(str.contains("Classic adventure story"));
    }

    @Test
    void testPhotoAndPhotoUriNullableFields() {
        CreateBookRequest req = new CreateBookRequest();

        assertNull(req.getPhoto());
        assertNull(req.getPhotoURI());

        MultipartFile mockPhoto = mock(MultipartFile.class);
        req.setPhoto(mockPhoto);
        req.setPhotoURI("https://img.com/pic.png");

        assertEquals(mockPhoto, req.getPhoto());
        assertEquals("https://img.com/pic.png", req.getPhotoURI());
    }

    @Test
    void testAuthorsListAssignment() {
        CreateBookRequest req = new CreateBookRequest();
        req.setAuthors(List.of(5L, 8L));

        assertEquals(2, req.getAuthors().size());
        assertEquals(List.of(5L, 8L), req.getAuthors());
    }
}
