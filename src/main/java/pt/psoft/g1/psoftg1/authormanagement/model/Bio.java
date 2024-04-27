package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class Bio {
    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 4096)
    String bio;
}
