package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "A Book")
public class BookView {

    @NotNull
    private String genre;

    @NotNull
    private String isbn;

    private String description;

    @NotNull
    private String title;

    @NotNull
    private String[] authors;
}
