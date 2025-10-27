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

    // Black Box Test
    @Test
    void testEqualsAndHashCode_WithSameValues() {
        AuthorView a1 = new AuthorView();
        a1.setAuthorNumber(42L);
        a1.setName("Author");
        a1.setBio("Bio");
        a1.setPhoto("photo.jpg");

        AuthorView a2 = new AuthorView();
        a2.setAuthorNumber(42L);
        a2.setName("Author");
        a2.setBio("Bio");
        a2.setPhoto("photo.jpg");

        AuthorView a3 = new AuthorView();
        a3.setAuthorNumber(42L);
        a3.setName("Author");
        a3.setBio("Bio");
        a3.setPhoto("photo.jpg");

        assertEquals(a1, a2);
        assertEquals(a2, a1);
        assertEquals(a2, a3);
        assertEquals(a1, a3);

        assertEquals(a1.hashCode(), a2.hashCode());
        assertEquals(a2.hashCode(), a3.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEquals_WhenAuthorNumberDiffers() {
        AuthorView a1 = new AuthorView();
        a1.setAuthorNumber(1L);
        a1.setName("Same");
        a1.setBio("Same");
        a1.setPhoto("same.jpg");

        AuthorView a2 = new AuthorView();
        a2.setAuthorNumber(2L);
        a2.setName("Same");
        a2.setBio("Same");
        a2.setPhoto("same.jpg");

        assertNotEquals(a1, a2);
    }

}
