package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreService;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.api.ListResponse;
import pt.psoft.g1.psoftg1.shared.api.UploadFileResponse;
import pt.psoft.g1.psoftg1.shared.model.FileUtils;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Books", description = "Endpoints for managing Books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private static final String IF_MATCH = "If-Match";
    private final BookService bookService;
    private final GenreService genreService;
    private final ConcurrencyService concurrencyService;
    private final FileStorageService fileStorageService;


    private final BookViewMapper bookViewMapper;
    private final GenreViewMapper genreViewMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.photo.max_size}")
    private long photoMaxSize;

    private final String[] validImageFormats = {"image/png", "image/jpeg"};


    @Operation(summary = "Register a new Book")
    @PutMapping(value = "/{isbn}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookView> create(@Valid CreateBookRequest resource, @PathVariable("isbn") String isbn) {

        UploadFileResponse up = null;

        //Guarantee that the client doesn't provide a link on the body, null = no photo or error
        resource.setPhotoURI(null);
        MultipartFile file = resource.getPhoto();

        String fileName = this.fileStorageService.getRequestPhoto(file);

        if (fileName != null) {
            resource.setPhotoURI(fileName);
        }

        Book book = null;
        try {
            book = bookService.create(resource, isbn);
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

    @Operation(summary = "Gets a specific Book by isbn")
    @GetMapping(value = "/{isbn}")
    public ResponseEntity<BookView> findByIsbn(@PathVariable final String isbn) {

        final var book = bookService.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException(Book.class, isbn));

        BookView bookView = bookViewMapper.toBookView(book);

        return ResponseEntity.ok()
                .eTag(Long.toString(book.getVersion()))
                .body(bookView);
    }

    @Operation(summary= "Gets a book photo")
    @GetMapping("/{isbn}/photo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getSpecificBookPhoto(@PathVariable final String isbn){

        Optional<Book> optBook = bookService.findByIsbn(isbn);
        if(optBook.isEmpty()) {
            throw new AccessDeniedException("A book could not be found with provided isbn");
        }

        Book book = optBook.get();

        //In case the user has no photo, just return a 200 OK without body
        if(book.getPhoto() == null) {
            return ResponseEntity.ok().build();
        }

        String photoPathString = uploadDir + "/" + book.getPhoto().getPhotoFile();
        Path photoPath = Paths.get(photoPathString);
        String fileFormat = photoPathString.split("\\.")[1];
        byte[] image = null;
        try {
            image = Files.readAllBytes(photoPath);
        } catch(IOException e) {
            throw new NotFoundException("Could not get book photo");
        }

        return ResponseEntity.ok().contentType(fileFormat.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG).body(image);
    }



    @Operation(summary = "Updates a specific Book")
    @PatchMapping(value = "/{isbn}")
    public ResponseEntity<BookView> updateBook(@PathVariable final String isbn,
                                               final WebRequest request,
                                               @Valid final UpdateBookRequest resource) {

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        MultipartFile file = resource.getPhoto();

        String fileName = this.getRequestPhoto(file);

        if (fileName != null) {
            resource.setPhotoURI(fileName);
        }

        Book  book;
        resource.setIsbn(isbn);
        try {
            book = bookService.update(resource, String.valueOf(concurrencyService.getVersionFromIfMatchHeader(ifMatchValue)));
        }catch (Exception e){
            throw new ConflictException("Could not update book: "+ e.getMessage());
        }
        return ResponseEntity.ok()
                .eTag(Long.toString(book.getVersion()))
                .body(bookViewMapper.toBookView(book));
    }

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

    @GetMapping("top5")
    public ListResponse<BookCountView> getTop5BooksLent() {
        return new ListResponse<>(bookViewMapper.toBookCountViewList(bookService.findTop5BooksLent()));
    }

    private String getRequestPhoto(MultipartFile file) {
        UploadFileResponse up = null;
        if(file != null) {
            if(file.getSize() > photoMaxSize) {
                throw new ValidationException("Attached photo can't be bigger than " + photoMaxSize + " bytes");
            }

            int formatIndex = -1;
            String fileContentHeader = file.getContentType();

            if(fileContentHeader == null) {
                throw new ValidationException("Unknown file content header");
            }

            for(int i = 0; i < validImageFormats.length; i++) {
                if(!fileContentHeader.equals(validImageFormats[i])) {
                    continue;
                }

                formatIndex = i;
                break;
            }

            if(formatIndex == -1) {
                throw new ValidationException("Images can only be png or jpeg");
            }

            String photoUUID = UUID.randomUUID().toString();

            try {
                up = FileUtils.doUploadFile(fileStorageService, photoUUID, file);
            } catch (Exception e) {
                return null;
                //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String fileFormat = validImageFormats[formatIndex].split("/")[1];
            return photoUUID+"."+fileFormat;
        }

        return null;
    }
}

