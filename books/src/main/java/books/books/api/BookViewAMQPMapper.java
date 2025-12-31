package books.books.api;

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
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "authorIds", expression = "java(mapAuthors(book.getAuthors()))")
    @Mapping(target = "version", source = "version")

    public abstract BookViewAMQP toBookViewAMQP(Book book);

    public abstract List<BookViewAMQP> toBookViewAMQP(List<Book> bookList);

    protected List<Long> mapAuthors(List<Author> authors) {
        return authors.stream().map(Author::getAuthorNumber).collect(Collectors.toList());
    }
}
