package shared.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.model.PhotoSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class PhotoSQLTest {

    private Photo photo;

    @BeforeEach
    void setup() {
        photo = new Photo(Paths.get("images/photo.png"));
    }

    // Black Box Test
    @Test
    void testPhotoSQLCreationFromDomain() {
        try (MockedStatic<IDGeneratorService> mocked = mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn("photo-id-123");

            PhotoSQL photoSQL = new PhotoSQL(photo);

            assertEquals("photo-id-123", photoSQL.getId());
            assertEquals(Paths.get("images/photo.png").toString(), photoSQL.getPhotoFile());
        }
    }

    // Black Box Test
    @Test
    void testToDomainConversion() {
        try (MockedStatic<IDGeneratorService> mocked = mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn("fake-id");

            PhotoSQL photoSQL = new PhotoSQL(photo);
            Photo domain = photoSQL.toDomain();

            assertNotNull(domain);
            assertEquals(Path.of("images/photo.png").toString(), domain.getPhotoFile());
        }
    }

    // White Box Test
    @Test
    void testFromDomainStaticMethod() {
        try (MockedStatic<IDGeneratorService> mocked = mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn("generated-id");

            PhotoSQL photoSQL = PhotoSQL.fromDomain(photo);

            assertEquals("generated-id", photoSQL.getId());
            assertEquals(Paths.get("images/photo.png").toString(), photoSQL.getPhotoFile());
        }
    }

    // White Box Test
    @Test
    void testProtectedEmptyConstructorWithReflection() throws Exception {
        Constructor<PhotoSQL> constructor = PhotoSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        PhotoSQL photoSQL = constructor.newInstance();

        assertNull(photoSQL.getId());
        assertNull(photoSQL.getPhotoFile());
    }

    // White Box Test
    @Test
    void testToDomainWithNullPhotoFileThrowsException() throws Exception {
        Constructor<PhotoSQL> constructor = PhotoSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        PhotoSQL photoSQL = constructor.newInstance();
        photoSQL.setPhotoFile(null);

        assertThrows(NullPointerException.class, photoSQL::toDomain);
    }
}
