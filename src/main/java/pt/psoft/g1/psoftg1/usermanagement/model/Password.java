package pt.psoft.g1.psoftg1.usermanagement.model;

public class Password {

    public Password(String newPasswd) {
        updatePassword(newPasswd);
    }

    public void updatePassword(String newPasswd) {
        String regexValid = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9\\W]).{8,}$";

        if (newPasswd == null || newPasswd.isEmpty() || !newPasswd.matches(regexValid)) {
            throw new IllegalArgumentException("The password must contain at least 8 characters, 1 upper case letter, and 1 number or special character.");
        }
    }
}
