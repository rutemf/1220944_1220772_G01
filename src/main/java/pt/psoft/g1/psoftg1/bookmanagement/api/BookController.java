package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreService;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.usermanagement.api.ListResponse;

import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.util.Optional;

@Tag(name = "Books", description = "Endpoints for managing Books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;

    private final BookViewMapper bookViewMapper;

    @RolesAllowed(Role.LIBRARIAN)
    @Operation(summary = "Register a new Book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookView> create(@Valid @RequestBody final CreateBookRequest resource) throws Exception {

        final var book = bookService.create(resource);

        final var newBookUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(book.getIsbn().toString())
                .build().toUri();

        return ResponseEntity.created(newBookUri)
                .eTag(Long.toString(book.getVersion()))
                .body(bookViewMapper.toBookView(book));
    }

    @RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Gets a specific Book by isbn")
    @GetMapping(value = "/{isbn}")
    public ResponseEntity<BookView> findByIsbn(@PathVariable final String isbn) throws Exception {

        final var book = bookService.findByIsbn(new Isbn(isbn))
                .orElseThrow(() -> new NotFoundException(Book.class, isbn));


        return ResponseEntity.ok()
                .eTag(Long.toString(book.getVersion()))
                .body(bookViewMapper.toBookView(book));
    }

    @RolesAllowed({Role.LIBRARIAN})
    @Operation(summary = "Updates a specific Book")
    @PatchMapping(value = "/{isbn}")
    public ResponseEntity<BookView> updateBook(@PathVariable final String isbn, @Valid @RequestBody final UpdateBookRequest resource) throws Exception {

        resource.setIsbn(isbn);
        final var book = bookService.update(resource);

        return ResponseEntity.ok()
                .eTag(Long.toString(book.getVersion()))
                .body(bookViewMapper.toBookView(book));
    }

    @RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Gets a specific Book by genre")
    @GetMapping
    public ListResponse<BookView> findByGenre(@RequestParam("genre") final String genre) throws Exception {

        Optional<Genre> optGenre = genreService.findByString(genre);
        final var books = bookService.findByGenre(optGenre.orElseThrow(() -> new NotFoundException(Book.class, genre)));
        return new ListResponse<>(bookViewMapper.toBookView(books));
    }
}

