package pt.psoft.g1.psoftg1.authormanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.usermanagement.api.ListResponse;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

@Tag(name = "Author", description = "Endpoints for managing Authors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {

    private static final String IF_MATCH = "If-Match";

    private final AuthorService authorService;
    private final AuthorViewMapper authorViewMapper;

    //Create
    @RolesAllowed(Role.LIBRARIAN)
    @Operation(summary = "Creates a new Author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorView> create(@Valid @RequestBody final CreateAuthorRequest resource) {

        final var author = authorService.create(resource);

        final var newauthorUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build().toUri();

        return ResponseEntity.created(newauthorUri).eTag(Long.toString(author.getVersion()))
                .body(authorViewMapper.toAuthorView(author));
    }


    //Update
    @RolesAllowed({Role.LIBRARIAN})
    @Operation(summary = "Updates a specific author")
    @PatchMapping(value = "/{authornumber}")
    public ResponseEntity<AuthorView> partialUpdate(@PathVariable final String authornumber, final WebRequest request, @Valid @RequestBody final
    UpdateAuthorRequest resource) throws Exception {

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }
        Author author;
        try {
            author = authorService.partialUpdate(Long.parseLong(authornumber),resource, Long.parseLong(ifMatchValue));
        }catch (Exception e){
            throw new ConflictException("Could not update author: "+ e.getMessage());
        }
        return ResponseEntity.ok()
                .eTag(Long.toString(author.getVersion()))
                .body(authorViewMapper.toAuthorView(author));
    }

    //Gets
    @RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Know an author’s detail given its author number")
    @GetMapping(value = "/{number}")
    public ResponseEntity<AuthorView> findByAuthorNumber(
            @PathVariable("number")
            @Parameter(description = "The authornumber of the Author to find") final Long number) {


        final var temp = authorService.findByAuthorNumber(number);
        if(temp.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Author author = temp.get();

        return ResponseEntity.ok()
                .eTag(Long.toString(author.getVersion()))
                .body(authorViewMapper.toAuthorView(author));
    }

    @RolesAllowed({Role.LIBRARIAN, Role.READER})
    @Operation(summary = "Search authors by name")
    @GetMapping
    public ListResponse<AuthorView> findByName(@RequestParam("name") final String name) throws Exception {

        final var authors = authorService.findByName(name);
        return new ListResponse<>(authorViewMapper.toAuthorView(authors));
    }

}
