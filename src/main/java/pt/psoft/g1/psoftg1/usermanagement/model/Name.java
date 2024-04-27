package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class Name {
    @NotNull
    @NotBlank
    String fullName;

    public Name() {
        // for ORM only
    }
}
