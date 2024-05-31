package pt.psoft.g1.psoftg1.bookmanagement.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

import java.util.List;


@Getter
@Data
@NoArgsConstructor
public class CreateBookRequest {

    @Setter
    private String description;

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    @NotNull
    private List<Long> authors;

    public CreateBookRequest(String title, String genre, List<Long> authors, String description) {
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.authors = authors;
    }
}
