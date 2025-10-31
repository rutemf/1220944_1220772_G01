package shared.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PhotoTest {

    // Black Box Test
    @Test
    void testConstructorAndGetter() {
        Path path = Paths.get("folder/image.jpg");
        Photo photo = new Photo(path);

        assertEquals(path.toString(), photo.getPhotoFile(), "Photo file should match the path string");
    }

    // Black Box Test
    @Test
    void testSetter() {
        Photo photo = new Photo(Paths.get("initial.jpg"));

        photo.setPhotoFile("newPhoto.png");

        assertEquals("newPhoto.png", photo.getPhotoFile(), "Photo file should match the updated value");
    }

    // White Box Test
    @Test
    void testPhotoFileInitializedFromPath() {
        Path path = Paths.get("folder", "subfolder", "photo.png");
        Photo photo = new Photo(path);

        assertNotNull(photo.getPhotoFile(), "photoFile should not be null");
        assertEquals(path.toString(), photo.getPhotoFile(), "photoFile should match");
    }

    // White Box Test
    @Test
    void testSetterChangesInternalState() {
        Path path = Paths.get("oldPhoto.jpg");
        Photo photo = new Photo(path);

        photo.setPhotoFile("updatedPhoto.jpg");

        assertEquals("updatedPhoto.jpg", photo.getPhotoFile(), "photoFile should be updated correctly");
    }

    // White Box Test
    @Test
    void testConstructorWithNullPath() {
        assertThrows(NullPointerException.class, () -> new Photo(null),
        "Constructor should throw NullPointerException when path is null");
    }
}
