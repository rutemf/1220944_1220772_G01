package readerManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderNumberTest {

    // Black Box Test
    @Test
    void testConstructorYearAndNumberFormatsCorrectly() {
        ReaderNumber rn = new ReaderNumber(2023, 17);
        assertEquals("2023/17", rn.toString());
        assertEquals("2023/17", rn.getReaderNumber());
    }

    // Black Box Test
    @Test
    void testConstructorOnlyNumberUsesCurrentYear() {
        int currentYear = LocalDate.now().getYear();
        ReaderNumber rn = new ReaderNumber(42);
        assertEquals(currentYear + "/42", rn.toString());
    }

    // Black Box Test
    @Test
    void testZeroAndNegativeNumbersAreCurrentlyAccepted() {
        ReaderNumber rnZero = new ReaderNumber(2025, 0);
        assertEquals("2025/0", rnZero.toString());

        ReaderNumber rnNegative = new ReaderNumber(2025, -5);
        assertEquals("2025/-5", rnNegative.toString());
    }

    // Black Box Test
    @Test
    void testLargeNumbersAreAccepted() {
        ReaderNumber rn = new ReaderNumber(2025, 123456);
        assertEquals("2025/123456", rn.toString());
    }

    // White Box Test
    @Test
    void testProtectedNoArgsConstructorLeavesReaderNumberNull() throws Exception {
        var ctor = ReaderNumber.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        ReaderNumber rn = ctor.newInstance();
        assertNull(rn.getReaderNumber());
        assertNull(rn.toString());
    }

    // White Box Test
    @Test
    void testToStringAndGetterReturnSameValue() {
        ReaderNumber rn = new ReaderNumber(1999, 88);
        assertEquals(rn.getReaderNumber(), rn.toString());
    }
}
