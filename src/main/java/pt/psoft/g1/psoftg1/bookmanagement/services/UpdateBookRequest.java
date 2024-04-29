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
    private String description;

    @Getter
    @NotBlank
    private String title;

    @Getter
    @NotBlank
    private String genre;

    //TODO: Fix this authorName list

    @Getter
    private String authorName;

    public UpdateBookRequest(String title, String genre, String authorName, String description) {
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.authorName = authorName;
    }
}
