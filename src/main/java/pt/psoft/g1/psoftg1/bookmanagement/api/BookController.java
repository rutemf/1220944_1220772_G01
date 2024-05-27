package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreService;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.usermanagement.api.ListResponse;

import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.util.List;
import java.util.Optional;

@Tag(name = "Books", description = "Endpoints for managing Books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private static final String IF_MATCH = "If-Match";
    private final BookService bookService;
    private final GenreService genreService;

    private final BookViewMapper bookViewMapper;

    @RolesAllowed(Role.LIBRARIAN)
    @Operation(summary = "Register a new Book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookView> create(@Valid @RequestBody final CreateBookRequest resource) {
        Book book = null;
        try {
            book = bookService.create(resource);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final var savedBook = bookService.save(book);
        final var newBookUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(savedBook.getIsbn().toString())
                .build().toUri();

        return ResponseEntity.created(newBookUri)
                .eTag(Long.toString(savedBook.getVersion()))
                .body(bookViewMapper.toBookView(savedBook));
    }

    @RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Gets a specific Book by isbn")
    @GetMapping(value = "/{isbn}")
    public ResponseEntity<BookView> findByIsbn(@PathVariable final String isbn) {

        final var book = bookService.findByIsbn(new Isbn(isbn))
                .orElseThrow(() -> new NotFoundException(Book.class, isbn));


        return ResponseEntity.ok()
                .eTag(Long.toString(book.getVersion()))
                .body(bookViewMapper.toBookView(book));
    }

    @RolesAllowed({Role.LIBRARIAN})
    @Operation(summary = "Updates a specific Book")
    @PatchMapping(value = "/{isbn}")
    public ResponseEntity<BookView> updateBook(@PathVariable final String isbn,
                                               final WebRequest request,
                                               @Valid @RequestBody final UpdateBookRequest resource) {

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }
        Book  book;
        resource.setIsbn(isbn);
        try {
            book = bookService.update(resource, ifMatchValue);
        }catch (Exception e){
            throw new ConflictException("Could not update book: "+ e.getMessage());
        }
        return ResponseEntity.ok()
                .eTag(Long.toString(book.getVersion()))
                .body(bookViewMapper.toBookView(book));
    }

    @RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Gets Books by title or genre")
    @GetMapping
    public ListResponse<BookView> findBooks(@RequestParam(value = "title", required = false) final String title,
                                            @RequestParam(value = "genre", required = false) final String genre) {

        if (title != null && genre != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only search by title or genre, not both");
        }

        List<Book> books;
        if (title != null) {
            books = bookService.findByTitle(title);
            if (books.isEmpty()) {
                throw new NotFoundException(Book.class, title);
            }
        } else if (genre != null) {
            Optional<Genre> optGenre = genreService.findByString(genre);
            books = bookService.findByGenre(optGenre.orElseThrow(() -> new NotFoundException(Book.class, genre)));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must provide either a title or a genre to search for books");
        }

        return new ListResponse<>(bookViewMapper.toBookView(books));
    }

    /*@RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Gets a specific Book by title")
    @GetMapping
    public ListResponse<BookView> findByTitle(@RequestParam("title") final String title) {
        List<Book> books = bookService.findByTitle(title);
        if(books.isEmpty()) {
            throw new NotFoundException(Book.class, title);
        }

        return new ListResponse<>(bookViewMapper.toBookView(books));
    }*/

    /*@RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Gets a specific Book by genre")
    @GetMapping
    public ListResponse<BookView> findByGenre(@RequestParam("genre") final String genre) {

        Optional<Genre> optGenre = genreService.findByString(genre);
        final var books = bookService.findByGenre(optGenre.orElseThrow(() -> new NotFoundException(Book.class, genre)));
        return new ListResponse<>(bookViewMapper.toBookView(books));
    }*/

    private Long getVersionFromIfMatchHeader(final String ifMatchHeader) {
        if (ifMatchHeader.startsWith("\"")) {
            return Long.parseLong(ifMatchHeader.substring(1, ifMatchHeader.length() - 1));
        }
        return Long.parseLong(ifMatchHeader);
    }
}

