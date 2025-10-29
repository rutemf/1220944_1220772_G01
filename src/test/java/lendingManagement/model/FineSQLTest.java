package lendingManagement.model;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FineSQLTest {

    // Black Box Test
    @Test
    void testFromDomain_MapsFieldsAndGeneratesId() {
        Book book = mock(Book.class);
        ReaderDetails reader = mock(ReaderDetails.class);
        Lending lending = new Lending(book, reader, 10, 50);

        Fine fine = new Fine(lending);
        fine.setCentsValue(999);

        LendingSQL lendingSQLMock = mock(LendingSQL.class);

        try (MockedStatic<IDGeneratorService> genStub = mockStatic(IDGeneratorService.class);
             MockedStatic<LendingSQL> lendingStub = mockStatic(LendingSQL.class)) {

            genStub.when(IDGeneratorService::generateIdSQL).thenReturn("SQL-ID-123");
            lendingStub.when(() -> LendingSQL.fromDomain(lending)).thenReturn(lendingSQLMock);

            FineSQL sql = FineSQL.fromDomain(fine);

            assertNotNull(sql);
            assertEquals("SQL-ID-123", sql.getId());
            assertEquals(50, sql.getFineValuePerDayInCents());
            assertEquals(999, sql.getCentsValue());
            assertSame(lendingSQLMock, sql.getLending());

            genStub.verify(IDGeneratorService::generateIdSQL, times(1));
            lendingStub.verify(() -> LendingSQL.fromDomain(lending), times(1));
        }
    }

    // Black Box Test
    @Test
    void testToDomain_MapsBackToFine() throws Exception {
        FineSQL sql = newInstanceViaReflection();
        sql.setId("SQL-ID-XYZ");
        sql.setFineValuePerDayInCents(75);
        sql.setCentsValue(300);

        Lending domainLending = mock(Lending.class);
        LendingSQL lendingSQL = mock(LendingSQL.class);
        when(lendingSQL.toDomain()).thenReturn(domainLending);
        sql.setLending(lendingSQL);

        Fine domain = sql.toDomain();

        assertNotNull(domain);
        assertSame(domainLending, domain.getLending());
        assertEquals(300, domain.getCentsValue());
        assertEquals(75, domain.getFineValuePerDayInCents());
        verify(lendingSQL, times(1)).toDomain();
    }

    // White Box Test
    @Test
    void testJpaNoArgsConstructorAndSetters() throws Exception {
        FineSQL sql = newInstanceViaReflection();

        assertNull(sql.getId());

        sql.setId("ABC");
        sql.setFineValuePerDayInCents(10);
        sql.setCentsValue(20);

        LendingSQL lendingSQL = mock(LendingSQL.class);
        sql.setLending(lendingSQL);

        assertEquals("ABC", sql.getId());
        assertEquals(10, sql.getFineValuePerDayInCents());
        assertEquals(20, sql.getCentsValue());
        assertSame(lendingSQL, sql.getLending());
    }

    private static FineSQL newInstanceViaReflection() throws Exception {
        Constructor<FineSQL> ctor = FineSQL.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }
}
