package lendingManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LendingNumberTest {

    // Black Box Test
    @Test
    void testValidYearAndSequential() {
        int currentYear = LocalDate.now().getYear();
        LendingNumber lendingNumber = new LendingNumber(currentYear, 42);
        assertEquals(currentYear + "/42", lendingNumber.toString());
    }

    // Black Box Test
    @Test
    void testValidStringConstructor() {
        LendingNumber lendingNumber = new LendingNumber("2023/15");
        assertEquals("2023/15", lendingNumber.toString());
    }

    // Black Box Test
    @Test
    void testSequentialZeroIsValid() {
        int year = LocalDate.now().getYear();
        LendingNumber lendingNumber = new LendingNumber(year, 0);
        assertEquals(year + "/0", lendingNumber.toString());
    }

    // White Box Test
    @Test
    void testInvalidYearTooEarly() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(1969, 1));
    }

    // White Box Test
    @Test
    void testInvalidYearFuture() {
        int nextYear = LocalDate.now().getYear() + 1;
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(nextYear, 1));
    }

    // White Box Test
    @Test
    void testNegativeSequentialThrowsException() {
        int year = LocalDate.now().getYear();
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(year, -10));
    }

    // White Box Test
    @Test
    void testStringConstructorInvalidFormatNoSlash() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("202315"));
    }

    // White Box Test
    @Test
    void testStringConstructorInvalidYearCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("20A3/5"));
    }

    // White Box Test
    @Test
    void testStringConstructorInvalidSequentialCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("2023/AB"));
    }

    // White Box Test
    @Test
    void testStringConstructorNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber((String) null));
    }

    // White Box Test
    @Test
    void testStringConstructorWithSlashAtWrongPosition() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new LendingNumber("20234/1"));
        assertTrue(ex.getMessage().contains("wrong format"));
    }

    // White Box Test
    @Test
    void testEdgeCaseMinimumValidYear() {
        LendingNumber lendingNumber = new LendingNumber(1970, 1);
        assertEquals("1970/1", lendingNumber.toString());
    }
}