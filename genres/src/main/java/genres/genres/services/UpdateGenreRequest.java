package genres.genres.services;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Data
@NoArgsConstructor
public class UpdateGenreRequest {

    @Setter
    private String genre;

    public UpdateGenreRequest(String genre) {
        this.genre = genre;
    }
}
