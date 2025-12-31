package auth_users.users.model;

public class Password {

    public Password(String newPasswd) {
        updatePassword(newPasswd);
    }

    public void updatePassword(String newPasswd) {
        String regexValid = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9\\W]).{8,}$";

        if (newPasswd == null || newPasswd.isEmpty() || !newPasswd.matches(regexValid)) {
            throw new IllegalArgumentException("Given Password is not valid. It must contain at least 8 characters, " +
            "1 upper case letter, 1 lower case letter and 1 number or special character.");
        }
    }
}