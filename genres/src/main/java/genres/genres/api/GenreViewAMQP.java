package genres.genres.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "A Genre form AMQP communication")
@NoArgsConstructor
public class GenreViewAMQP {

    @NotNull
    private String genre;

    @NotNull
    private Long version;

    public GenreViewAMQP(String genre) {
        this.genre = genre;
    }
}
