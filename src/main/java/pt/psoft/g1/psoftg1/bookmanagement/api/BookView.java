package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A Book")
public class BookView {
    private String genre;
    private String isbn;
    private String description;
    private String title;
    private String[] authors;
}
