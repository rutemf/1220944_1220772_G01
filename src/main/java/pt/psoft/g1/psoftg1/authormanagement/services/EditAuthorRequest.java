package pt.psoft.g1.psoftg1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditAuthorRequest {
    //TODO: Why can these values be null????? An edit may not need to update them all.
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    private String shortbio;

    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = 32)
    private Name name;
}
