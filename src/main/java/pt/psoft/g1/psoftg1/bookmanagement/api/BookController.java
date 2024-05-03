package pt.psoft.g1.psoftg1.bookmanagement.api;
/*
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreService;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;

@Tag(name = "Books", description = "Endpoints to manage books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
class BookController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookViewMapper bookViewMapper;

    @Operation(summary = "Registers a book")
    @PutMapping
    public ResponseEntity<Object> createBook(@RequestBody CreateBookRequest bookRequest) {
        Book book = null;
        try {
            book = bookService.create(bookRequest);
            bookService.save(book);
        } catch (Exception e) {
            ErrorResponse responseBody = new ErrorResponse("Error creating book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

        BookView bookView = bookViewMapper.toBookView(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookView);
    }

    @Operation(summary = "Updates a book")
    @PutMapping
    public ResponseEntity<Object> updateBook(@RequestBody UpdateBookRequest bookRequest) {
        Book book = null;
        try {
            book = bookService.update(bookRequest);
            bookService.save(book);
        } catch (Exception e) {
            ErrorResponse responseBody = new ErrorResponse("Error updating book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

        BookView bookView = bookViewMapper.toBookView(book);

        return ResponseEntity.status(HttpStatus.OK).body(bookView);
    }
    /*
    @Operation(summary = "Get books details by Isbn")
    @GetMapping
    public ResponseEntity<Object> getBook(@RequestBody UpdateBookRequest bookRequest) {
        Book book = null;
        try {
            book = bookService.update(bookRequest);
            bookService.save(book);
        } catch (Exception e) {
            ErrorResponse responseBody = new ErrorResponse("Error updating book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

        BookView bookView = bookViewMapper.toBookView(book);

        return ResponseEntity.status(HttpStatus.OK).body(bookView);
    }*/
/*
    private static class ErrorResponse {

        public ErrorResponse(String error) {
        }
    }
}
*/
