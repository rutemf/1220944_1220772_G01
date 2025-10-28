package lendingManagement.model;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.model.*;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class FineNoSQLTest {

    // Black Box Test
    @Test
    void testFromDomain_MapsFieldsAndGeneratesId() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);
        Lending lending = new Lending(book, reader, 10, 50);

        Fine fine = new Fine(lending);
        fine.setCentsValue(999);

        LendingNoSQL lendingNoSQLMock = mock(LendingNoSQL.class);

        try (MockedStatic<IDGeneratorService> genStub = mockStatic(IDGeneratorService.class);
             MockedStatic<LendingNoSQL> lendingStub = mockStatic(LendingNoSQL.class)) {

            genStub.when(IDGeneratorService::generateIdNoSQL).thenReturn("SQL-ID-123");
            lendingStub.when(() -> LendingNoSQL.fromDomain(lending)).thenReturn(lendingNoSQLMock);

            FineNoSQL noSQL = FineNoSQL.fromDomain(fine);

            assertNotNull(noSQL);
            assertEquals("SQL-ID-123", noSQL.getId());
            assertEquals(50, noSQL.getFineValuePerDayInCents());
            assertEquals(999, noSQL.getCentsValue());
            assertSame(lendingNoSQLMock, noSQL.getLending());

            genStub.verify(IDGeneratorService::generateIdNoSQL, times(1));
            lendingStub.verify(() -> LendingNoSQL.fromDomain(lending), times(1));
        }
    }

    // Black Box Test
    @Test
    void testToDomain_MapsBackToFine() throws Exception {
        FineNoSQL noSQL = newInstanceViaReflection();
        noSQL.setId("SQL-ID-XYZ");
        noSQL.setFineValuePerDayInCents(75);
        noSQL.setCentsValue(300);

        Lending domainLending = mock(Lending.class);
        LendingNoSQL lendingNoSQL = mock(LendingNoSQL.class);
        when(lendingNoSQL.toDomain()).thenReturn(domainLending);
        noSQL.setLending(lendingNoSQL);

        Fine domain = noSQL.toDomain();

        assertNotNull(domain);
        assertSame(domainLending, domain.getLending());
        assertEquals(300, domain.getCentsValue());
        assertEquals(75, domain.getFineValuePerDayInCents());
        verify(lendingNoSQL, times(1)).toDomain();
    }

    // White Box Test
    @Test
    void testJpaNoArgsConstructorAndSetters() throws Exception {
        FineNoSQL noSQL = newInstanceViaReflection();

        assertNull(noSQL.getId());

        noSQL.setId("ABC");
        noSQL.setFineValuePerDayInCents(10);
        noSQL.setCentsValue(20);

        LendingNoSQL lendingNoSQL = mock(LendingNoSQL.class);
        noSQL.setLending(lendingNoSQL);

        assertEquals("ABC", noSQL.getId());
        assertEquals(10, noSQL.getFineValuePerDayInCents());
        assertEquals(20, noSQL.getCentsValue());
        assertSame(lendingNoSQL, noSQL.getLending());
    }

    private static FineNoSQL newInstanceViaReflection() throws Exception {
        Constructor<FineNoSQL> ctor = FineNoSQL.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }
}
