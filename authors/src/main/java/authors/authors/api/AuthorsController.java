package authors.authors.api;

import authors.authors.model.Author;
import authors.authors.services.AuthorService;
import authors.authors.services.CreateAuthorRequest;
import authors.authors.services.SearchAuthorsQuery;
import authors.authors.services.UpdateAuthorRequest;
import authors.exceptions.ConflictException;
import authors.shared.api.ListResponse;
import authors.shared.model.Name;
import authors.shared.services.ConcurrencyService;
import authors.shared.services.SearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Authors", description = "Endpoints for managing Authors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorsController {

    private final AuthorService authorService;
    private final ConcurrencyService concurrencyService;
    private final AuthorsViewMapper authorsViewMapper;

    @Operation(summary = "Register a new Author")
    @PutMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorsView> create(CreateAuthorRequest resource) {

        Author author;
        try {
            author = authorService.create(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final var newGenreUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(author.getName()).build().toUri();

        return ResponseEntity.created(newGenreUri).eTag(Long.toString(author.getVersion())).body(authorsViewMapper.toAuthorsView(author));
    }

    @Operation(summary = "Updates a specific author")
    @PatchMapping(value = "/{name}")
    public ResponseEntity<AuthorsView> updateAuthor(@PathVariable final Name authorName, final WebRequest request,
                                                    @Valid final UpdateAuthorRequest resource) {

        final String ifMatchValue = request.getHeader(ConcurrencyService.IF_MATCH);

        if (ifMatchValue == null || ifMatchValue.isEmpty() || ifMatchValue.equals("null")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        Author author;
        resource.setName(authorName);

        try {
            author = authorService.update(resource,
                    concurrencyService.getVersionFromIfMatchHeader(ifMatchValue));
        } catch (Exception e) {
            throw new ConflictException("Could not update author: " + e.getMessage());
        }

        return ResponseEntity.ok().eTag(Long.toString(author.getVersion())).body(authorsViewMapper.toAuthorsView(author));
    }

    @Operation(summary = "Gets a specific Author by Name")
    @GetMapping(value = "/{name}")
    public ResponseEntity<AuthorsView> findByAuthorName(@PathVariable final String authorName) {

        final var author = authorService.findByName(authorName);

        AuthorsView bookView = authorsViewMapper.toAuthorsView(author);

        return ResponseEntity.ok().eTag(Long.toString(author.getVersion())).body(bookView);
    }

    @Operation(summary = "Search all authors")
    @PostMapping("/search") //nao devia ser get?
    public ListResponse<AuthorsView> searchGenres(@RequestBody final SearchRequest<SearchAuthorsQuery> request) {
        final var genreList = authorService.searchAuthors(request.getPage(), request.getQuery());
        return new ListResponse<>(authorsViewMapper.toAuthorsView(genreList));
    }
}
