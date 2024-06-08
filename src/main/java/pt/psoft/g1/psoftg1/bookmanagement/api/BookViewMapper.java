package pt.psoft.g1.psoftg1.bookmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BookViewMapper extends MapperInterface {
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "authors", expression = "java(mapAuthors(book.getAuthors()))")
    @Mapping(target = "_links", expression = "java(mapLinks(book))")
    @Mapping(target = "photo", expression = "java(generatePhotoUrl(book))")

    public abstract BookView toBookView(Book book);

    public abstract List<BookView> toBookView(List<Book> bookList);

    @Mapping(target = "bookView", source = "book")
    public abstract BookCountView toBookCountView(BookCountDTO bookCountView);

    public abstract List<BookCountView> toBookCountView(List<BookCountView> bookCountView);

    @Mapping(target = "_links", expression = "java(mapLinks(book))")
    @Mapping(target = "authors", expression = "java(mapAuthors(book.getAuthors()))")
    public abstract BookShortView toBookShortView(Book book);

    public abstract List<BookShortView> toBookShortView(List<Book> books);

    public abstract List<BookCountView> toBookCountViewList(List<BookCountDTO> bookCountDTOList);

    protected List<String> mapAuthors(List<Author> authors) {
        return authors.stream()
                .map(Author::getName)
                .collect(Collectors.toList());
    }


    public Map<String, Object> mapLinks(final Book book) {
        String bookUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/books/")
                .path(book.getIsbn())
                .toUriString();

        Map<String, Object> links = new HashMap<>();
        links.put("self", bookUri);

        List<Map<String, String>> authorLinks = book.getAuthors().stream()
                .map(author -> {
                    String authorUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/authors/")
                            .path(author.getAuthorNumber().toString())
                            .toUriString();
                    Map<String, String> authorLink = new HashMap<>();
                    authorLink.put("href", authorUri);
                    return authorLink;
                })
                .collect(Collectors.toList());

        links.put("authors", authorLinks);
        links.put("photo", generatePhotoUrl(book));


        return links;
    }

    protected String generatePhotoUrl(Book book) {
        String isbn = book.getIsbn();
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/books/{isbn}/photo").buildAndExpand(isbn).toUri().toString();
    }
}
