package shared.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.dataschema.ForbiddenNameNoSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForbiddenNameNoSQLTest {

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
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn(GENERATED_ID);

            ForbiddenNameNoSQL noSQL = ForbiddenNameNoSQL.fromDomain(domainName);
            ForbiddenName result = noSQL.toDomain();

            assertEquals(domainName.getForbiddenName(), result.getForbiddenName());
        }
    }

    // Black Box Test
    @Test
    void testNullForbiddenNameHandling() {
        ForbiddenName domain = new ForbiddenName(null);

        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn(GENERATED_ID);

            ForbiddenNameNoSQL noSQL = new ForbiddenNameNoSQL(domain);
            assertNull(noSQL.getForbiddenName());

            ForbiddenName result = noSQL.toDomain();
            assertNull(result.getForbiddenName());
        }
    }

    // White Box Test
    @Test
    void testConstructorSetsFieldsCorrectly() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn(GENERATED_ID);

            ForbiddenNameNoSQL noSQL = new ForbiddenNameNoSQL(domainName);

            assertEquals(GENERATED_ID, noSQL.getId());
            assertEquals("admin", noSQL.getForbiddenName());
        }
    }

    // White Box Test
    @Test
    void testFromDomainCreatesEquivalentObject() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn(GENERATED_ID);

            ForbiddenNameNoSQL noSQL = ForbiddenNameNoSQL.fromDomain(domainName);

            assertEquals("admin", noSQL.getForbiddenName());
            assertEquals(GENERATED_ID, noSQL.getId());
        }
    }

    // White Box Test
    @Test
    void testToDomainConvertsCorrectly() {
        try (MockedStatic<IDGeneratorService> mocked = Mockito.mockStatic(IDGeneratorService.class)) {
            mocked.when(IDGeneratorService::generateIdNoSQL).thenReturn(GENERATED_ID);

            ForbiddenNameNoSQL noSQL = new ForbiddenNameNoSQL(domainName);
            ForbiddenName domainConverted = noSQL.toDomain();

            assertNotNull(domainConverted);
            assertEquals("admin", domainConverted.getForbiddenName());
        }
    }

    // White Box Test
    @Test
    void testProtectedConstructorExists() throws Exception {
        var constructor = ForbiddenNameNoSQL.class.getDeclaredConstructor();
        assertFalse(constructor.canAccess(null));
        constructor.setAccessible(true);
        ForbiddenNameNoSQL instance = constructor.newInstance();
        assertNotNull(instance);
    }
}
