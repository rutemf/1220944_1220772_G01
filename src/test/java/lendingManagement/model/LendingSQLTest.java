package lendingManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingSQL;

import static org.junit.jupiter.api.Assertions.*;

public class LendingSQLTest {

    // White Box Test
    @Test
    void testEmptyConstructor() throws Exception {
        var constructor = LendingSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        LendingSQL lendingSQL = constructor.newInstance();

        assertNull(lendingSQL.getId());
        assertNull(lendingSQL.getBook());
        assertNull(lendingSQL.getReaderDetails());
    }
}
