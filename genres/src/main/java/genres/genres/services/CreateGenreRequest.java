package genres.genres.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@Schema(description = "A DTO for creating a Genre")
public class CreateGenreRequest {

    @NotBlank
    @Schema( maxLength = 100)
    private String genre;
}
