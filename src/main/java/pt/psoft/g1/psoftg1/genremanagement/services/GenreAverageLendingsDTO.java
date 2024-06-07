package pt.psoft.g1.psoftg1.genremanagement.services;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@Data
@AllArgsConstructor
public class GenreAverageLendingsDTO {
    @NotNull
    private String genre;
    private String averageLendings;

    public GenreAverageLendingsDTO(Genre genre, Double averageLendings){
        this.genre = genre.toString();
        this.averageLendings = String.format("%.1f", averageLendings);
    }
}
