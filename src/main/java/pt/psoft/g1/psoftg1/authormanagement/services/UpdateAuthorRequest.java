package pt.psoft.g1.psoftg1.authormanagement.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 4096)
    private String bio;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 150)
    private String name;

}
