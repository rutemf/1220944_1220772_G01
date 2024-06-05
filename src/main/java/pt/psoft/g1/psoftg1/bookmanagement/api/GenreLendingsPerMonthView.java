package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "A Genre and its lendings count per month.")
@AllArgsConstructor
public class GenreLendingsPerMonthView {
    @NotNull
    private String genre;

    private int year;

    private int month;

    private long lendingsCount;

}
