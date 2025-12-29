package authors.authors.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAuthorsQuery {
    private String name;
    private Long authorNumber;
}
