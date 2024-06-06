package pt.psoft.g1.psoftg1.genremanagement.services;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreLendingsPerMonthDTO {
    @NotNull
    private String genre;

    private int year;

    private int month;

    private long lendingsCount;

}
