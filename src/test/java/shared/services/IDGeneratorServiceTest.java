package shared.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.services.IDBase65GeneratorService;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class IDGeneratorServiceTest {

    // Black Box Test
    @Test
    void testGenerateIdSQLProducesBase65Id() {
        String id = IDBase65GeneratorService.generateIdSQL();
        assertNotNull(id);
        assertTrue(id.matches("[A-Za-z0-9+/#]+"));
    }

    // Black Box Test
    @Test
    void testGenerateIdNoSQLProducesTimestampHex() {
        String id = IDBase65GeneratorService.generateIdNoSQL();
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
        String id1 = IDBase65GeneratorService.generateIdNoSQL();
        Thread.sleep(5);
        String id2 = IDBase65GeneratorService.generateIdNoSQL();

        long t1 = Long.parseLong(id1.split("-")[0]);
        long t2 = Long.parseLong(id2.split("-")[0]);
        assertTrue(t2 >= t1);
    }

    // White Box Test
    @Test
    void testGenerateRandomHex6UsingReflection() throws Exception {
        Method method = IDBase65GeneratorService.class.getDeclaredMethod("generateRandomHex6");
        method.setAccessible(true);
        String result = (String) method.invoke(IDBase65GeneratorService.class);

        assertEquals(6, result.length());
        assertTrue(result.matches("[0-9A-F]{6}"));
    }

    // White Box Test
    @Test
    void testGenerateRandomNumeric6UsingReflection() throws Exception {
        Method method = IDBase65GeneratorService.class.getDeclaredMethod("generateRandomNumeric6");
        method.setAccessible(true);
        String result = (String) method.invoke(IDBase65GeneratorService.class);

        assertEquals(6, result.length());
        assertTrue(result.matches("\\d{6}"));
    }

    // White Box Test
    @Test
    void testEncodeBase65KnownValue() throws Exception {
        Method method = IDBase65GeneratorService.class.getDeclaredMethod("encodeBase65", String.class);
        method.setAccessible(true);

        String encoded = (String) method.invoke(IDBase65GeneratorService.class, "123456");
        assertNotNull(encoded);
        assertEquals(8, encoded.length());
        assertTrue(encoded.matches("[A-Za-z0-9+/#]{8}"));
    }

    // White Box Test
    @Test
    void testGenerateIdSQLUniqueness() {
        String id1 = IDBase65GeneratorService.generateIdSQL();
        String id2 = IDBase65GeneratorService.generateIdSQL();
        assertNotEquals(id1, id2);
    }

    // White Box Test
    @Test
    void testEncodeBase65Padding() throws Exception {
        Method method = IDBase65GeneratorService.class.getDeclaredMethod("encodeBase65", String.class);
        method.setAccessible(true);

        String encoded = (String) method.invoke(IDBase65GeneratorService.class, "000000");
        assertEquals(8, encoded.length());
        assertTrue(encoded.matches("[A-Za-z0-9+/#]{8}"));
    }
}
