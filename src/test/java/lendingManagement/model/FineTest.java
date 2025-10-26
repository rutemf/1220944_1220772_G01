package lendingManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class FineTest {

    private Lending lending;

    @BeforeEach
    void setup() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);
        lending = new Lending(book, reader, 10, 50);
    }

    // Black Box Test
    @Test
    void testFineCreation() {
        Fine fine = new Fine(lending);

        assertNotNull(fine.getLending());
        assertEquals(50, fine.getFineValuePerDayInCents());
        assertEquals(0, fine.getCentsValue());
    }

    // Black Box Test
    @Test
    void testFineValueForLateReturn() {
        lending.setReturnedDate(lending.getLimitDate().plusDays(3));
        Fine fine = new Fine(lending);

        assertEquals(3 * 50, fine.getCentsValue());
        assertEquals(50, fine.getFineValuePerDayInCents());
    }

    // Black Box Test
    @Test
    void testFineValueWhenReturnedOnTime() {
        lending.setReturnedDate(lending.getLimitDate());
        Fine fine = new Fine(lending);

        assertEquals(0, fine.getCentsValue());
    }

    // White Box Test
    @Test
    void testToStringIncludesCentsAndLendingNumber() {
        lending.setReturnedDate(lending.getLimitDate().plusDays(2));
        Fine fine = new Fine(lending);

        String expected = "Fine: " + fine.getCentsValue() + " cents for lending " + lending.getLendingNumber();
        assertEquals(expected, fine.toString());
    }

    // White Box Test
    @Test
    void testFineWithZeroDelay() {
        lending.setReturnedDate(null);
        Fine fine = new Fine(lending);

        assertEquals(0, fine.getCentsValue());
        assertEquals(50, fine.getFineValuePerDayInCents());
    }
}
