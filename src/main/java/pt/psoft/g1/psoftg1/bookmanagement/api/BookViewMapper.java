package pt.psoft.g1.psoftg1.bookmanagement.api;

import org.mapstruct.Mapper;
import pt.psoft.g1.psoftg1.auth.api.AuthApi;
import pt.psoft.g1.psoftg1.bookmanagement.model.*;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class BookViewMapper {
    public abstract BookView toBookView(Book book);

    public abstract List<BookView> toBookView(List<Book> bookList);

    public Integer mapOptInt(final Optional<Integer> i) {
        return i.orElse(null);
    }

    public Long mapOptLong(final Optional<Long> i) {
        return i.orElse(null);
    }

    public String mapOptString(final Optional<String> i) {
        return i.orElse(null);
    }

    public String map(Isbn isbn) {
        return isbn.toString();
    }

    public String map(Title title) {
        return title.toString();
    }

    public String map(Description description) {
        return description.toString();
    }

    public String map(Genre genre) {
        return genre.toString();
    }

    public String map(Author author) {
        return author.getName().toString();
    }

    public String[] map(List<Author> authorList) {
        String[] authorNames = new String[authorList.size()];
        for(int i = 0; i < authorList.size(); i++) {
            authorNames[i] = authorList.get(i).getName().toString();
        }

        return authorNames;
    }
}
