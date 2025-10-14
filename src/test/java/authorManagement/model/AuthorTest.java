package authorManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;


import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {

    // Black Box Test
    @Test
    void testApplyPatchUpdatesAllFields() {
        Author a = new Author(1L, new Name("Old Name"), new Bio("Old Bio"));

        UpdateAuthorRequest req = new UpdateAuthorRequest();
        req.setName("New Name");
        req.setBio("New Bio");
        req.setPhotoURI("https://cdn.example.com/img.jpg");

        a.applyPatch(req);

        assertEquals("New Name", a.getName().toString());
        assertEquals("New Bio",  a.getBio().toString());
    }

    // Black Box Test
    @Test
    void testApplyPatchUpdatesOnlyName() {
        Author a = new Author(2L, new Name("N0"), new Bio("B0"));

        UpdateAuthorRequest req = new UpdateAuthorRequest();
        req.setName("N1");

        a.applyPatch(req);

        assertEquals("N1", a.getName().toString());
        assertEquals("B0", a.getBio().toString());
    }

    // Black Box Test
    @Test
    void testApplyPatchUpdatesOnlyBio() {
        Author a = new Author(3L, new Name("N0"), new Bio("B0"));

        UpdateAuthorRequest req = new UpdateAuthorRequest();
        req.setBio("B1");

        a.applyPatch(req);

        assertEquals("N0", a.getName().toString());
        assertEquals("B1", a.getBio().toString());
    }

    // Black Box Test
    @Test
    void testApplyPatchAllNullDoesNothing() {
        Author a = new Author(5L, new Name("N0"), new Bio("B0"));

        UpdateAuthorRequest req = new UpdateAuthorRequest();
        a.applyPatch(req);

        assertEquals("N0", a.getName().toString());
        assertEquals("B0", a.getBio().toString());
    }

    // White Box Test
    @Test
    void testApplyPatchReplacesValueObjectsWhenPresent() {
        Name oldName = new Name("Old");
        Bio oldBio = new Bio("OldB");
        Author a = new Author(7L, oldName, oldBio);

        UpdateAuthorRequest req = new UpdateAuthorRequest();
        req.setName("New");
        req.setBio("NewB");

        a.applyPatch(req);

        assertNotSame(oldName, a.getName());
        assertNotSame(oldBio, a.getBio());
        assertEquals("New",  a.getName().toString());
        assertEquals("NewB", a.getBio().toString());
    }

    // White Box Test
    @Test
    void testApplyPatchKeepsInstancesWhenNullInPatch() {
        Name oldName = new Name("Keep");
        Bio oldBio = new Bio("KeepB");
        Author a = new Author(8L, oldName, oldBio);

        UpdateAuthorRequest req = new UpdateAuthorRequest();
        a.applyPatch(req);

        assertSame(oldName, a.getName());
        assertSame(oldBio, a.getBio());
    }
}
