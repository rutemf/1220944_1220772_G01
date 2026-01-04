package books.books.api;

import books.books.services.CreateAuthor;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import books.authors.model.Author;
import books.books.model.Book;
import books.shared.api.MapperInterface;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BookViewAMQPMapper extends MapperInterface {

    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "genre", source = "genre.genre") // assume que Book tem Genre com getName()
    @Mapping(target = "authors", expression = "java(mapAuthors(book.getAuthors()))")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "_links", ignore = true)

    public abstract BookViewAMQP toBookViewAMQP(Book book);

    public abstract List<BookViewAMQP> toBookViewAMQP(List<Book> bookList);

    protected @NotNull List<String> mapAuthors(List<Author> authors) {
        if (authors == null) return null;
        return authors.stream()
                .map(Author::getName) // pega só o nome
                .collect(Collectors.toList());
    }
}
