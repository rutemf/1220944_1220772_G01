package books.books.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "A Book form AMQP communication")
@NoArgsConstructor
public class BookViewAMQP {
    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private List<String> authors;

    @NotNull
    private String genre;

    @NotNull
    private Long version;

    @Setter
    @Getter
    private Map<String, Object> _links = new HashMap<>();

    public BookViewAMQP(String isbn, String title, String description, List<String> authors, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.genre = genre;
    }
}
