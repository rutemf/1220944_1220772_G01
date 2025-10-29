package shared.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.model.FileUtils;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {

    // White Box Test
    @Test
    void testDoUploadFileThrowsExceptionWhenParametersNull() {
        Exception exception = assertThrows(Exception.class, () ->
                FileUtils.doUploadFile(null, null, null)
        );

        assertEquals("Could not get reference of fileStorageService, id or file", exception.getMessage());
    }
}
