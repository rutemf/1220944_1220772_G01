package auth_users.shared.model;

import java.util.regex.Pattern;

public class StringUtilsCustom {

    public static boolean isAlphanumeric(String str) {
        String pattern = "^[\\p{L}0-9\\s'-]*$";
        return Pattern.matches(pattern, str);
    }
}