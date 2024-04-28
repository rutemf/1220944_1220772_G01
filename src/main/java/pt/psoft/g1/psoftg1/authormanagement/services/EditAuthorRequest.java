package pt.psoft.g1.psoftg1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditAuthorRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    private String shortbio;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 32)
    private Name name;
}
