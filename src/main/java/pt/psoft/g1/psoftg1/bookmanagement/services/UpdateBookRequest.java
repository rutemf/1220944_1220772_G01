package pt.psoft.g1.psoftg1.bookmanagement.services;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;


@Getter
@Data
@NoArgsConstructor
public class UpdateBookRequest {

    @Setter
    private String description;

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    //TODO: Fix this authorName list

    private String authorName;

    public UpdateBookRequest(String title, String genre, String authorName, String description) {
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.authorName = authorName;
    }
}
