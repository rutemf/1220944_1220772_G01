package pt.psoft.g1.psoftg1.bookmanagement.services;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;


@Data
@NoArgsConstructor
public class UpdateBookRequest {

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

    public UpdateBookRequest(Title title, Genre genre, String authorName, Description description) {
        this.genre = genre;
        this.authorName = authorName;
        this.description = description;
        this.authorName = authorName;
    }
}
