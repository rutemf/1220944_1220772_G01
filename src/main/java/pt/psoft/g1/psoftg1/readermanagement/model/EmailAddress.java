package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;

@Embeddable
public class EmailAddress {
    @Email
    String address;
}
