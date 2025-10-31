package readerManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class EmailAddressTest {

    // Black Box Test
    @Test
    void testAllArgsConstructor() {
        EmailAddress emailAddress = new EmailAddress("user@example.com");

        try {
            var field = EmailAddress.class.getDeclaredField("address");
            field.setAccessible(true);
            String value = (String) field.get(emailAddress);
            assertEquals("user@example.com", value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access private field");
        }
    }

    // White Box Test
    @Test
    void testProtectedConstructorWithReflection() throws Exception {
        Constructor<EmailAddress> constructor = EmailAddress.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        EmailAddress emailAddress = constructor.newInstance();

        assertNotNull(emailAddress);
    }
}
