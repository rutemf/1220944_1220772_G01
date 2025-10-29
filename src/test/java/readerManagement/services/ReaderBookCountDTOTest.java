package readerManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderBookCountDTOTest {

    // Black Box Test
    @Test
    void testNoArgsConstructor() {
        ReaderBookCountDTO dto = new ReaderBookCountDTO();
        assertNotNull(dto);
        assertNull(dto.getReaderDetails());
        assertEquals(0L, dto.getLendingCount());
    }

    // Black Box Test
    @Test
    void testAllArgsConstructorAndGetters() {
        ReaderDetails details = createValidReaderDetails(10, "910000000");
        long count = 25L;

        ReaderBookCountDTO dto = new ReaderBookCountDTO(details, count);

        assertNotNull(dto);
        assertEquals(details, dto.getReaderDetails());
        assertEquals(count, dto.getLendingCount());
    }

    // Black Box Test
    @Test
    void testSettersFromDataAnnotation() {
        ReaderBookCountDTO dto = new ReaderBookCountDTO();
        ReaderDetails details = createValidReaderDetails(11, "911111111");
        long count = 50L;

        dto.setReaderDetails(details);
        dto.setLendingCount(count);

        assertEquals(details, dto.getReaderDetails());
        assertEquals(count, dto.getLendingCount());
    }

    // Black Box Test
    @Test
    void testEqualityAndHashCodeDifference() {
        ReaderDetails details = createValidReaderDetails(13, "913333333");

        ReaderBookCountDTO dto1 = new ReaderBookCountDTO(details, 10L);
        ReaderBookCountDTO dto2 = new ReaderBookCountDTO(details, 20L);

        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    // Black Box Test
    @Test
    void testEqualityWithNullFields() {
        ReaderBookCountDTO dto1 = new ReaderBookCountDTO(null, 5L);
        ReaderBookCountDTO dto2 = new ReaderBookCountDTO(null, 5L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    private ReaderDetails createValidReaderDetails(int readerNum, String phoneNumber) {
        try {
            return new ReaderDetails(
                    readerNum,
                    new Reader("test" + readerNum + "@email.com", "Passwd123!"),
                    "1990-01-01",
                    phoneNumber,
                    true,
                    false,
                    false,
                    null,
                    Collections.emptyList()
            );
        } catch (Exception e) {
            fail("Failed to create ReaderDetails: " + e.getMessage());
            return null;
        }
    }
}
