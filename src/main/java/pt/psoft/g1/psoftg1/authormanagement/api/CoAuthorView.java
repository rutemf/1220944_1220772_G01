
package pt.psoft.g1.psoftg1.authormanagement.api;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;

        import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoAuthorView {
    private Long authorId;
    private String authorName;
    private List<BookView> books;
}

