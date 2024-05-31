package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

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
    private List<String> authors;
}
