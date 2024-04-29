package pt.psoft.g1.psoftg1.bookmanagement.services;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;


@Data
@NoArgsConstructor
public class CreateBookRequest {
    @Setter
    @Getter
    private String isbn;

    @Getter
    @Setter
    private String description;

    @Getter
    @NonNull
    @NotBlank
    private String title;

    @Getter
    @NonNull
    @NotBlank
    private String genre;

    @Getter
    private String authorName;

    public CreateBookRequest(String isbn, String title, String genre, String authorName, String description) {
        this.isbn = isbn;
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.authorName = authorName;
    }
}
