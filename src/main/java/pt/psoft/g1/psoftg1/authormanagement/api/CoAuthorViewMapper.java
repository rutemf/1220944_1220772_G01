package pt.psoft.g1.psoftg1.authormanagement.api;

import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewMapper;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoAuthorViewMapper {
    private final BookViewMapper bookViewMapper;

    public CoAuthorViewMapper(BookViewMapper bookViewMapper) {
        this.bookViewMapper = bookViewMapper;
    }

    public CoAuthorView toCoAuthorView(Author author, List<Book> books) {
        List<BookView> bookViews = books.stream()
                .map(bookViewMapper::toBookView)
                .collect(Collectors.toList());

        return new CoAuthorView(author.getId(), author.getName(), bookViews);
    }
}
