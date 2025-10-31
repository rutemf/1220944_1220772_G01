package shared.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.dataschema.PhotoNoSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class PhotoNoSQLTest {

    private Photo photo;

    @BeforeEach
    void setup() {
        photo = new Photo(Paths.get("images/photo.png"));
    }

    // Black Box Test
    @Test
    void testPhotoNoSQLCreationFromDomain() {
        try (MockedStatic<IDGeneratorService> mocked = mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn("photo-id-123");

            PhotoNoSQL photoNoSQL = new PhotoNoSQL(photo);

            assertEquals("photo-id-123", photoNoSQL.getId());
            assertEquals(Paths.get("images/photo.png").toString(), photoNoSQL.getPhotoFile());
        }
    }

    // Black Box Test
    @Test
    void testToDomainConversion() {
        try (MockedStatic<IDGeneratorService> mocked = mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn("fake-id");

            PhotoNoSQL photoNoSQL = new PhotoNoSQL(photo);
            Photo domain = photoNoSQL.toDomain();

            assertNotNull(domain);
            assertEquals(Path.of("images/photo.png").toString(), domain.getPhotoFile());
        }
    }

    // White Box Test
    @Test
    void testFromDomainStaticMethod() {
        try (MockedStatic<IDGeneratorService> mocked = mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn("generated-id");

            PhotoNoSQL photoNoSQL = PhotoNoSQL.fromDomain(photo);

            assertEquals("generated-id", photoNoSQL.getId());
            assertEquals(Paths.get("images/photo.png").toString(), photoNoSQL.getPhotoFile());
        }
    }

    // White Box Test
    @Test
    void testProtectedEmptyConstructorWithReflection() throws Exception {
        Constructor<PhotoNoSQL> constructor = PhotoNoSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        PhotoNoSQL photoNoSQL = constructor.newInstance();

        assertNull(photoNoSQL.getId());
        assertNull(photoNoSQL.getPhotoFile());
    }

    // White Box Test
    @Test
    void testToDomainWithNullPhotoFileThrowsException() throws Exception {
        Constructor<PhotoNoSQL> constructor = PhotoNoSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        PhotoNoSQL photoNoSQL = constructor.newInstance();
        photoNoSQL.setPhotoFile(null);

        assertThrows(NullPointerException.class, photoNoSQL::toDomain);
    }
}
