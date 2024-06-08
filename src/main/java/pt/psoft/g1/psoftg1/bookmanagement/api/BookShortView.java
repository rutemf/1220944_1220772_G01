package pt.psoft.g1.psoftg1.bookmanagement.api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Getter
public class BookShortView {
    private String title;
    private String isbn;
    private List<String> authors;
    @Setter
    private Map<String, Object> _links = new HashMap<>();
}
