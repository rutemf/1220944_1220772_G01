package pt.psoft.g1.psoftg1.authormanagement.api;

import java.net.URISyntaxException;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
/*
@Tag(name = "Authors", description = "Endpoints for managing authors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
class AuthorController {

    private static final String IF_MATCH = "If-Match";

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService service;

    @Operation(summary = "Creates a new Author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorView> create(@Valid @RequestBody final CreateAuthorRequest resource) {

        final var Author = service.create(resource);

        final var newAuthorUri = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(Author.getId().toString())
                .build().toUri();

        return ResponseEntity.created(newAuthorUri).eTag(Long.toString(Author.getVersion()))
                .body(AuthorViewMapper.toAuthorView(Author));
    }

    private Long getVersionFromIfMatchHeader(final String ifMatchHeader) {
        if (ifMatchHeader.startsWith("\"")) {
            return Long.parseLong(ifMatchHeader.substring(1, ifMatchHeader.length() - 1));
        }
        return Long.parseLong(ifMatchHeader);
    }

    @Operation(summary = "Gets a specific Author by name")
    @GetMapping(value = "/{Authorname}")
    public ResponseEntity<AuthorView> findByName(
            @PathVariable("Authorname") @Parameter(description = "The Authorname of the Author to find") final string Authorname) {
        final var Author = service.findOne(Authorname).orElseThrow(() -> new NotFoundException(Author.class, Authorname));

        return ResponseEntity.ok().eTag(Long.toString(Author.getVersion())).body(AuthorViewMapper.toAuthorView(Author));
    }

    @Operation(summary = "Gets a specific Author's Details by AuthorsNumber")
    @GetMapping(value = "/{AuthorNumber}")
    public ResponseEntity<AuthorView> findByAuthorNumber(
            @PathVariable("AuthorNumber") @Parameter(description = "The AuthorNumber of the Author to find") final string AuthorNumber) {
        final var Author = service.findOne(AuthorNumber).orElseThrow(() -> new NotFoundException(Author.class, AuthorNumber));

        return ResponseEntity.ok().eTag(Long.toString(Author.getVersion())).body(AuthorViewMapper.toAuthorView(Author));
    }
    @Operation(summary = "Partially updates an existing Author")
    @PatchMapping(value = "/{AuthorNumber}")
    public ResponseEntity<BarView> partialUpdate(final WebRequest request,
                                                 @PathVariable("AuthorNumber") @Parameter(description = "The AuthorNumber of the Author to update") final Long id,
                                                 @Valid @RequestBody final EditBarRequest resource) {
        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        final var bar = service.partialUpdate(AuthorNumber, resource, getVersionFromIfMatchHeader(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(bar.getVersion())).body(barViewMapper.toBarView(bar));
    }*/

    /* DÚVIDAS - isto é "real" ate que ponto? eu nao vou querer/puder dar update no autor number. Apago?
    @Operation(summary = "Fully replaces an existing bar. If the specified AuthorNumber does not exist does nothing and returns 400.")
    @PutMapping(value = "/{id}")*/
    /*public ResponseEntity<BarView> upsert(final WebRequest request,
                                          @PathVariable("id") @Parameter(description = "The id of the bar to replace/create") final Long id,
                                          @Valid @RequestBody final EditBarRequest resource) {
        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            // no if-match header was sent, so we are in INSERT mode
            return ResponseEntity.badRequest().build();
        }
        // if-match header was sent, so we are in UPDATE mode
        final var bar = service.update(id, resource, getVersionFromIfMatchHeader(ifMatchValue));
        return ResponseEntity.ok().eTag(Long.toString(bar.getVersion())).body(barViewMapper.toBarView(bar));
    }*/


    /* DÚVIDAS - isto é necessario para alguem? se n for, é apagar como apaguei o delete author

    @Operation(summary = "Gets all Authors")
    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = "application/json",
            // Use the `array` property instead of `schema`
            array = @ArraySchema(schema = @Schema(implementation = AuthorView.class))) })
    @GetMapping
    public Iterable<AuthorView> findAll() {
        return AuthorViewMapper.toAuthorView(service.findAll());
    }
*/


//}
