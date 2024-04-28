package pt.psoft.g1.psoftg1.readermanagement.model;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneNumber {
    String number;

    public PhoneNumber(String number) throws Exception {
        setPhoneNumber(number);
    }

    protected PhoneNumber() {}

    private void setPhoneNumber(String number) throws Exception {
        if(!number.startsWith("9") || !number.startsWith("2") || number.length() != 9) {
            throw new Exception("Phone number is not valid");
        }

        this.number = number;
    }


}
