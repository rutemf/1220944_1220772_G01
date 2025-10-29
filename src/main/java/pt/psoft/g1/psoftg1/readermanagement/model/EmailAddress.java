package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailAddress {

    @Email
    private String address;

    protected EmailAddress() { }
}