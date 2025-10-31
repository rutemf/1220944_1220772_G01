package readerManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.model.BirthDate;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BirthDateTest {

    // Black Box Test
    @Test
    void testConstructorWithValidLocalDate() {
        LocalDate pastDate = LocalDate.now().minusYears(20);
        BirthDate birthDate = new BirthDate(pastDate);
        assertEquals(pastDate, birthDate.getBirthDate());
    }

    // Black Box Test
    @Test
    void testConstructorWithYearMonthDayValid() {
        BirthDate birthDate = new BirthDate(1990, 5, 20);
        assertEquals(LocalDate.of(1990, 5, 20), birthDate.getBirthDate());
    }

    // Black Box Test
    @Test
    public void testConstructorWithValidString() {
        String validDate = "2000-01-01";
        BirthDate birthDate = new BirthDate(validDate);
        assertEquals(LocalDate.parse(validDate), birthDate.getBirthDate());
    }

    // Black Box Test
    @Test
    public void testToStringReturnsDateString() {
        LocalDate date = LocalDate.of(1980, 7, 15);
        BirthDate birthDate = new BirthDate(date);
        assertEquals(date.toString(), birthDate.toString());
    }

    // White Box Test
    @Test
    void testConstructorWithLocalDateTooYoung() {
        LocalDate futureDate = LocalDate.now().minusYears(10);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate(futureDate);
        });
        assertTrue(thrown.getMessage().contains("at least 12"));
    }

    // White Box Test
    @Test
    public void testConstructorWithYearMonthDayTooYoung() {
        int year = LocalDate.now().minusYears(5).getYear();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate(year, 1, 1);
        });
        assertTrue(thrown.getMessage().contains("at least 12"));
    }

    // White Box Test
    @Test
    public void testConstructorWithInvalidStringFormat() {
        String invalidDate = "01-01-2000";
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate(invalidDate);
        });
        assertTrue(thrown.getMessage().contains("valid format"));
    }

    // White Box Test
    @Test
    public void testConstructorWithStringTooYoung() {
        String tooYoungDate = LocalDate.now().minusYears(5).toString();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate(tooYoungDate);
        });
        assertTrue(thrown.getMessage().contains("at least 12"));
    }

    // White Box Test
    @Test
    void testProtectedConstructor() throws Exception {
        Constructor<BirthDate> constructor = BirthDate.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        BirthDate birthDate = constructor.newInstance();

        assertNull(birthDate.getBirthDate());
    }
}
