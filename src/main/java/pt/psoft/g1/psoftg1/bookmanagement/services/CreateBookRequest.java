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
    private Isbn isbn;

    @Getter
    @Setter
    private Description description;

    @Getter
    @NonNull
    @NotBlank
    private Title title;

    @Getter
    @NonNull
    @NotBlank
    private Genre genre;

    @Getter
    private String authorName;

    public CreateBookRequest(Isbn isbn, Title title, Genre genre, String authorName, Description description) {
        this.isbn = isbn;
        this.genre = genre;
        this.authorName = authorName;
        this.description = description;
        this.authorName = authorName;
    }
}
