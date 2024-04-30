package pt.psoft.g1.psoftg1.bookmanagement.services;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;


@Getter
@Data
@NoArgsConstructor
public class CreateBookRequest {
    @Setter
    private String isbn;

    @Setter
    private String description;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String genre;

    private String authorName;

    public CreateBookRequest(String isbn, @NonNull String title, @NonNull String genre, String authorName, String description) {
        this.isbn = isbn;
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.authorName = authorName;
    }
}
