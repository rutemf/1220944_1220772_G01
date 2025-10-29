package shared.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenNameSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import static org.junit.jupiter.api.Assertions.*;

public class ForbiddenNameSQLTest {

    private static final String GENERATED_ID = "123-abc";
    private ForbiddenName domainName;

    @BeforeEach
    void setup() {
        domainName = new ForbiddenName("admin");
    }

    // Black Box Test
    @Test
    void testRoundTripConversionPreservesData() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn(GENERATED_ID);

            ForbiddenNameSQL sql = ForbiddenNameSQL.fromDomain(domainName);
            ForbiddenName result = sql.toDomain();

            assertEquals(domainName.getForbiddenName(), result.getForbiddenName());
        }
    }

    // Black Box Test
    @Test
    void testNullForbiddenNameHandling() {
        ForbiddenName domain = new ForbiddenName(null);

        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn(GENERATED_ID);

            ForbiddenNameSQL sql = new ForbiddenNameSQL(domain);
            assertNull(sql.getForbiddenName());

            ForbiddenName result = sql.toDomain();
            assertNull(result.getForbiddenName());
        }
    }

    // White Box Test
    @Test
    void testConstructorSetsFieldsCorrectly() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn(GENERATED_ID);

            ForbiddenNameSQL sql = new ForbiddenNameSQL(domainName);

            assertEquals(GENERATED_ID, sql.getId());
            assertEquals("admin", sql.getForbiddenName());
        }
    }

    // White Box Test
    @Test
    void testFromDomainCreatesEquivalentObject() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn(GENERATED_ID);

            ForbiddenNameSQL sql = ForbiddenNameSQL.fromDomain(domainName);

            assertEquals("admin", sql.getForbiddenName());
            assertEquals(GENERATED_ID, sql.getId());
        }
    }

    // White Box Test
    @Test
    void testToDomainConvertsCorrectly() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdSQL).thenReturn(GENERATED_ID);

            ForbiddenNameSQL sql = new ForbiddenNameSQL(domainName);
            ForbiddenName domainConverted = sql.toDomain();

            assertNotNull(domainConverted);
            assertEquals("admin", domainConverted.getForbiddenName());
        }
    }

    // White Box Test
    @Test
    void testProtectedConstructorExists() throws Exception {
        var constructor = ForbiddenNameSQL.class.getDeclaredConstructor();
        assertFalse(constructor.canAccess(null));
        constructor.setAccessible(true);
        ForbiddenNameSQL instance = constructor.newInstance();
        assertNotNull(instance);
    }
}
