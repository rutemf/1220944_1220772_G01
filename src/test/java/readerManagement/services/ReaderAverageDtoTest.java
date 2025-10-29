package readerManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderAverageDto;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderAverageDtoTest {

   // Black Box Test
    @Test
    void testCreationAndGetters() {
        ReaderDetails details = createValidReaderDetails(1, "911223344");
        Long count = 15L;

        ReaderAverageDto dto = new ReaderAverageDto();
        dto.setReaderView(details);
        dto.setLendingCount(count);

        assertNotNull(dto, "O DTO não deve ser nulo.");
        assertEquals(details, dto.getReaderView(), "O ReaderDetails deve ser o setado.");
        assertEquals(count, dto.getLendingCount(), "A contagem de empréstimos deve ser a setada.");
    }

    // Black Box Test
    @Test
    void testLendingCountCanBeNull() {
        ReaderDetails details = createValidReaderDetails(2, "911223355");

        ReaderAverageDto dto = new ReaderAverageDto();
        dto.setReaderView(details);
        dto.setLendingCount(null);

        assertNull(dto.getLendingCount(), "O lendingCount deve ser nulo.");
        assertNotNull(dto.getReaderView(), "O ReaderDetails deve ser não nulo.");
    }

    // Black Box Test
    @Test
    void testEqualityAndHashCodeDifference() {
        ReaderDetails detailsA = createValidReaderDetails(4, "911223377");

        ReaderAverageDto dto1 = new ReaderAverageDto();
        dto1.setReaderView(detailsA);
        dto1.setLendingCount(10L);

        ReaderAverageDto dto2 = new ReaderAverageDto();
        dto2.setReaderView(detailsA);
        dto2.setLendingCount(20L);

        assertNotEquals(dto1, dto2, "DTOs com contagem diferente não devem ser iguais.");
        assertNotEquals(dto1.hashCode(), dto2.hashCode(), "Hash codes devem ser diferentes.");

        ReaderDetails detailsB = createValidReaderDetails(5, "911223388");
        dto2.setReaderView(detailsB);
        dto2.setLendingCount(10L);

        assertNotEquals(dto1, dto2, "DTOs com ReaderDetails diferente não devem ser iguais.");
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCodeEdgeCases() {
        ReaderDetails details = createValidReaderDetails(6, "911223399");

        ReaderAverageDto dto1 = new ReaderAverageDto();
        dto1.setReaderView(details);
        dto1.setLendingCount(5L);

        assertNotEquals(null, dto1, "O DTO não deve ser igual a null.");

        int initialHashCode = dto1.hashCode();

        dto1.setLendingCount(100L);
        assertNotEquals(initialHashCode, dto1.hashCode(), "O HashCode deve mudar após modificação.");

        dto1.setLendingCount(5L);
        assertEquals(initialHashCode, dto1.hashCode(), "O HashCode deve retornar ao valor original.");
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
            fail("Falha ao criar ReaderDetails: " + e.getMessage());
            return null;
        }
    }
}
