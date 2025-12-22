package readers.shared.model;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.regex.Pattern;

public class StringUtilsCustom {

    private static final PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public static boolean isAlphanumeric(String str) {
        String pattern = "^[\\p{L}0-9\\s'-]*$";
        return Pattern.matches(pattern, str);
    }

    public static String sanitizeHtml(String str) {
        return sanitizer.sanitize(str);
    }
}