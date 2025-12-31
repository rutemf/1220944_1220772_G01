package readers.readers.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailAddress {

    @Email
    private String address;

    protected EmailAddress() { }
}