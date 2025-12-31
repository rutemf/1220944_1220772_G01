package genres.genres.api;

import genres.exceptions.ConflictException;
import genres.genres.model.Genre;
import genres.genres.services.CreateGenreRequest;
import genres.genres.services.GenreService;
import genres.genres.services.SearchGenreQuery;
import genres.genres.services.UpdateGenreRequest;
import genres.shared.api.ListResponse;
import genres.shared.services.ConcurrencyService;
import genres.shared.services.SearchRequest;
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

@Tag(name = "Genres", description = "Endpoints for managing Genres")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenresController {

    private final GenreService genreService;
    private final ConcurrencyService concurrencyService;
    private final GenreViewMapper genreViewMapper;

    @Operation(summary = "Register a new Genre")
    @PutMapping(value = "/{genreName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenreView> create(CreateGenreRequest resource) {

        Genre genre;
        try {
            genre = genreService.create(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final var newGenreUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(genre.getGenre()).build().toUri();

        return ResponseEntity.created(newGenreUri).eTag(Long.toString(genre.getVersion())).body(genreViewMapper.toGenreView(genre));
    }

    @Operation(summary = "Updates a specific Genre")
    @PatchMapping(value = "/{genreName}")
    public ResponseEntity<GenreView> updateGenre(@PathVariable final String genreName, final WebRequest request,
                                               @Valid final UpdateGenreRequest resource) {

        final String ifMatchValue = request.getHeader(ConcurrencyService.IF_MATCH);

        if (ifMatchValue == null || ifMatchValue.isEmpty() || ifMatchValue.equals("null")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        Genre genre;
        resource.setGenre(genreName);

        try {
            genre = genreService.update(resource,
                    concurrencyService.getVersionFromIfMatchHeader(ifMatchValue));
        } catch (Exception e) {
            throw new ConflictException("Could not update genre: " + e.getMessage());
        }

        return ResponseEntity.ok().eTag(Long.toString(genre.getVersion())).body(genreViewMapper.toGenreView(genre));
    }

    @Operation(summary = "Gets a specific Genre by Name")
    @GetMapping(value = "/{genreName}")
    public ResponseEntity<GenreView> findByGenreName(@PathVariable final String genreName) {

        final var genre = genreService.findByGenre(genreName);

        GenreView bookView = genreViewMapper.toGenreView(genre);

        return ResponseEntity.ok().eTag(Long.toString(genre.getVersion())).body(bookView);
    }

    @Operation(summary = "Search all genders")
    @PostMapping("/search") //nao devia ser get?
    public ListResponse<GenreView> searchGenres(@RequestBody final SearchRequest<SearchGenreQuery> request) {
        final var genreList = genreService.searchGenres(request.getPage(), request.getQuery());
        return new ListResponse<>(genreViewMapper.toGenreView(genreList));
    }
}
