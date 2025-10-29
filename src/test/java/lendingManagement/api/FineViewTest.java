package lendingManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.lendingmanagement.api.FineView;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingView;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FineViewTest {

    private LendingView createLending() {
        LendingView lending = new LendingView();
        lending.setLendingNumber("2025/1");
        lending.setBookTitle("Clean Code");
        lending.setStartDate(LocalDate.of(2025, 10, 1));
        return lending;
    }

    // Black Box Test
    @Test
    void testSettersAndGetters() {
        FineView fine = new FineView();
        LendingView lending = createLending();

        fine.setCentsValue(500);
        fine.setLending(lending);

        assertEquals(500, fine.getCentsValue());
        assertEquals(lending, fine.getLending());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        LendingView lending = createLending();

        FineView fine1 = new FineView();
        fine1.setCentsValue(250);
        fine1.setLending(lending);

        FineView fine2 = new FineView();
        fine2.setCentsValue(250);
        fine2.setLending(lending);

        assertEquals(fine1, fine2);
        assertEquals(fine1.hashCode(), fine2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentValues() {
        LendingView lending1 = createLending();
        LendingView lending2 = createLending();
        lending2.setLendingNumber("2025/2");

        FineView fine1 = new FineView();
        fine1.setCentsValue(100);
        fine1.setLending(lending1);

        FineView fine2 = new FineView();
        fine2.setCentsValue(200);
        fine2.setLending(lending2);

        assertNotEquals(fine1, fine2);
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        FineView fine = new FineView();
        LendingView lending = createLending();
        fine.setCentsValue(750);
        fine.setLending(lending);

        String str = fine.toString();
        assertTrue(str.contains("750"));
        assertTrue(str.contains("Clean Code"));
        assertTrue(str.contains("lending"));
        assertTrue(str.contains("centsValue"));
    }
}
