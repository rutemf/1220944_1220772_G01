package pt.psoft.g1.psoftg1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditBookRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    private String shortNote;

    @Size(min = 0, max = 4096)
    private String description;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 32)
    private Title title;
}
