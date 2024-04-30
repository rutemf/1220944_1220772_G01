package pt.psoft.g1.psoftg1.authormanagement.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UpdateAuthorRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 4096)
    private String bio;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 32)
    private String name;

    private byte[] photo;

    public UpdateAuthorRequest(String name, String bio, byte[] photo) {
        this.name = name;
        this.bio = bio;
        this.photo = photo;
    }
}
