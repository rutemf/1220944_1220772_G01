package lendingManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNoSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingSQL;

import static org.junit.jupiter.api.Assertions.assertNull;

public class LendingNoSQLTest {

    // White Box Test
    @Test
    void testEmptyConstructor() throws Exception {
        var constructor = LendingNoSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        LendingNoSQL lendingNoSQL = constructor.newInstance();

        assertNull(lendingNoSQL.getId());
        assertNull(lendingNoSQL.getBook());
        assertNull(lendingNoSQL.getReaderDetails());
    }
}
