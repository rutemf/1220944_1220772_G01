package authorManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorView;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorViewTest {

    // Black Box Test
    @Test
    void testAuthorViewSettersAndGetters() {
        AuthorView author = new AuthorView();
        author.setAuthorNumber(1L);
        author.setName("Agatha Christie");
        author.setBio("English novelist, famous for detective stories like Poirot.");
        author.setPhoto("photo_url.jpg");

        assertEquals(1L, author.getAuthorNumber());
        assertEquals("Agatha Christie", author.getName());
        assertEquals("English novelist, famous for detective stories like Poirot.", author.getBio());
        assertEquals("photo_url.jpg", author.getPhoto());
        assertNotNull(author.get_links());
        assertTrue(author.get_links().isEmpty());
    }

    // Black Box Test
    @Test
    void testLinksCanBeModified() {
        AuthorView author = new AuthorView();
        author.get_links().put("self", "/api/v1/authors/1");

        Map<String, Object> links = author.get_links();
        assertEquals("/api/v1/authors/1", links.get("self"));
    }
}
