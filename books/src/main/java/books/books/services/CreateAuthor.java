package books.books.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "A DTO for creating an Author inside a Book")
public class CreateAuthor {
    @NotBlank
    private String name;

    @NotBlank
    private String bio;
}
