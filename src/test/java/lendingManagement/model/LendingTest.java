package lendingManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class LendingTest {

    private Lending lending;

    @BeforeEach
    void setup() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);
        lending = new Lending(book, reader, 10, 100);
    }

    // Black Box Test
    @Test
    void testLendingCreation() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);

        Lending lending = new Lending(book, reader, 7, 50);

        assertNotNull(lending.getLendingNumber());
        assertEquals(book, lending.getBook());
        assertEquals(reader, lending.getReaderDetails());
        assertEquals(7, lending.getDaysUntilReturn());
        assertEquals(0, lending.getDaysOverdue());
        assertNull(lending.getReturnedDate());
        assertEquals(50, lending.getFineValuePerDayInCents());
    }

    // Black Box Test
    @Test
    void testFineCalculationForLateReturn() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);

        Lending lending = new Lending(book, reader, 3, 20);

        lending.setReturnedDate(lending.getLimitDate().plusDays(2));

        Optional<Integer> fine = lending.getFineValueInCents();
        assertTrue(fine.isPresent());
        assertEquals(40, fine.get());
    }

    // Black Box Test
    @Test
    void testNoFineIfReturnedOnTime() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);

        Lending lending = new Lending(book, reader, 5, 10);
        lending.setReturnedDate(lending.getLimitDate());

        Optional<Integer> fine = lending.getFineValueInCents();
        assertEquals(0, fine.get());
    }

    // Black Box Test
    @Test
    void testDaysDelayedIsNeverNegative() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);

        Lending lending = new Lending(book, reader, 5, 10);
        lending.setReturnedDate(lending.getStartDate().minusDays(2));

        assertEquals(0, lending.getDaysDelayed());
    }

    // White Box Test
    @Test
    void testGetDaysDelayedWhenReturnedOnTime() {
        lending.setReturnedDate(lending.getLimitDate());
        assertEquals(0, lending.getDaysDelayed());
    }

    // White Box Test
    @Test
    void testGetDaysDelayedWhenReturnedLate() {
        lending.setReturnedDate(lending.getLimitDate().plusDays(3));
        assertEquals(3, lending.getDaysDelayed());
    }

    // White Box Test
    @Test
    void testGetDaysDelayedWhenNotReturnedYet() {
        lending.setReturnedDate(null);
        assertTrue(lending.getDaysDelayed() >= 0);
    }

    // White Box Test
    @Test
    void testGetFineValueInCents() {
        lending.setReturnedDate(lending.getLimitDate().plusDays(4));
        Optional<Integer> fine = lending.getFineValueInCents();
        assertTrue(fine.isPresent());
        assertEquals(4 * 100, fine.get());
    }

    // White Box Test
    @Test
    void testToString() {
        assertEquals("Lending: " + lending.getLendingNumber(), lending.toString());
    }
}
