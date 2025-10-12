package shared.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class IDGeneratorServiceTest {

    private final IDGeneratorService idGeneratorService = new IDGeneratorService();

    // Black Box Test
    @Test
    void testGenerateIdSQLProducesBase65Id() {
        String id = idGeneratorService.generateIdSQL();
        assertNotNull(id);
        assertTrue(id.matches("[A-Za-z0-9+/#]+"));
    }

    // Black Box Test
    @Test
    void testGenerateIdNoSQLProducesTimestampHex() {
        String id = idGeneratorService.generateIdNoSQL();
        assertNotNull(id);
        assertTrue(id.contains("-"));
        String[] parts = id.split("-");
        assertEquals(2, parts.length);

        assertTrue(parts[0].matches("\\d+"));
        assertTrue(parts[1].matches("[0-9A-F]{6}"));
    }

    // Black Box Test
    @Test
    void testGenerateIdNoSQLTimeProgression() throws InterruptedException {
        String id1 = idGeneratorService.generateIdNoSQL();
        Thread.sleep(5);
        String id2 = idGeneratorService.generateIdNoSQL();

        long t1 = Long.parseLong(id1.split("-")[0]);
        long t2 = Long.parseLong(id2.split("-")[0]);
        assertTrue(t2 >= t1);
    }

    // White Box Test
    @Test
    void testGenerateRandomHex6UsingReflection() throws Exception {
        Method method = IDGeneratorService.class.getDeclaredMethod("generateRandomHex6");
        method.setAccessible(true);
        String result = (String) method.invoke(idGeneratorService);

        assertEquals(6, result.length());
        assertTrue(result.matches("[0-9A-F]{6}"));
    }

    // White Box Test
    @Test
    void testGenerateRandomNumeric6UsingReflection() throws Exception {
        Method method = IDGeneratorService.class.getDeclaredMethod("generateRandomNumeric6");
        method.setAccessible(true);
        String result = (String) method.invoke(idGeneratorService);

        assertEquals(6, result.length());
        assertTrue(result.matches("\\d{6}"));
    }

    // White Box Test
    @Test
    void testEncodeBase65KnownValue() throws Exception {
        Method method = IDGeneratorService.class.getDeclaredMethod("encodeBase65", String.class);
        method.setAccessible(true);

        String encoded = (String) method.invoke(idGeneratorService, "123456");
        assertNotNull(encoded);
        assertEquals(8, encoded.length());
        assertTrue(encoded.matches("[A-Za-z0-9+/#]{8}"));
    }

    // White Box Test
    @Test
    void testGenerateIdSQLUniqueness() {
        String id1 = idGeneratorService.generateIdSQL();
        String id2 = idGeneratorService.generateIdSQL();
        assertNotEquals(id1, id2);
    }

    // White Box Test
    @Test
    void testEncodeBase65Padding() throws Exception {
        Method method = IDGeneratorService.class.getDeclaredMethod("encodeBase65", String.class);
        method.setAccessible(true);

        String encoded = (String) method.invoke(idGeneratorService, "000000");
        assertEquals(8, encoded.length());
        assertTrue(encoded.matches("[A-Za-z0-9+/#]{8}"));
    }
}
