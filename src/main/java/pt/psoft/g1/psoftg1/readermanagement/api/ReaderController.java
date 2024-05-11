package pt.psoft.g1.psoftg1.readermanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingView;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.usermanagement.api.ListResponse;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@Tag(name = "Readers", description = "Endpoints to manage readers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reader")
class ReaderController {
    private final ReaderService readerService;
    private final UserService userService;
    private final ReaderViewMapper readerViewMapper;
    private final LendingService lendingService;
    private final LendingViewMapper lendingViewMapper;

    @Operation(summary = "Gets all readers")
    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = "application/json",
            // Use the `array` property instead of `schema`
            array = @ArraySchema(schema = @Schema(implementation = ReaderView.class))) })
    @GetMapping
    //This is just for testing purposes, therefore admin role has been set
    @RolesAllowed(Role.ADMIN)
    public ListResponse<ReaderView> findAll() {
        return new ListResponse<>(readerViewMapper.toReaderView(readerService.findAll()));
    }

    @RolesAllowed(Role.LIBRARIAN)
    @Operation(summary = "Gets all reader by number")
    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = "application/json",
            // Use the `array` property instead of `schema`
            array = @ArraySchema(schema = @Schema(implementation = ReaderView.class))) })
    @GetMapping(value="{year}/{seq}")
    //This is just for testing purposes, therefore admin role has been set
    //@RolesAllowed(Role.LIBRARIAN)
    public ResponseEntity<ReaderView> findByReaderNumber(@PathVariable("year")
                                                           @Parameter(description = "The year of the Reader to find")
                                                           final Integer year,
                                                       @PathVariable("seq")
                                                           @Parameter(description = "The sequencial of the Reader to find")
                                                           final Integer seq) {
        String readerNumber = year+"/"+seq;
        Optional<ReaderDetails> readerDetailsOpt = readerService.findByReaderNumber(readerNumber);
        if(readerDetailsOpt.isEmpty()) {
            throw new NotFoundException("Could not find reader from specified reader number");
        }

        return new ResponseEntity<>(readerViewMapper.toReaderView(readerDetailsOpt.get()), HttpStatus.OK);
    }

    @RolesAllowed(Role.LIBRARIAN)
    public ListResponse<ReaderView> findByReaderName(@RequestParam("name") final String name) {
        SearchUsersQuery query = new SearchUsersQuery();
        query.setFullName(name);
        Page page = new Page();

        List<User> userList = this.userService.searchUsers(page, query);
        List<ReaderDetails> readerDetailsList = new ArrayList<>();

        for(User user : userList) {
            Optional<ReaderDetails> readerDetails = this.readerService.findByUsername(user.getUsername());
            if(readerDetails.isPresent()) {
                readerDetailsList.add(readerDetails.get());
            }
        }

        if(readerDetailsList.isEmpty()) {
            throw new NotFoundException("Could not find reader with name " + name);
        }

        return new ListResponse<ReaderView>(readerViewMapper.toReaderView(readerDetailsList));
    }

    @Operation(summary = "Creates a reader")
    @PostMapping
    public ResponseEntity<ReaderView> createReader(@RequestBody CreateReaderRequest readerRequest) {
        CreateUserRequest userRequest = new CreateUserRequest();

        userRequest.setUsername(readerRequest.getUsername());
        userRequest.setPassword(readerRequest.getPassword());
        userRequest.setRole(Role.READER);
        userRequest.setName(readerRequest.getFullName());

        User user = null;

        try {
            user = userService.create(userRequest);
        } catch(Exception e) {
            throw new ConflictException("Error creating User for Reader: " + e.getMessage());
        }

        //TODO: User is created as User and not with Role, even though we're using the same approach to create a new user on the repo.
        //TODO: For some reason, it's enabled attribute is set to false... WHYYYY
        //TODO: Manually force this data until fixed.
        user.setEnabled(true);
        user.addAuthority(new Role(Role.READER));

        ReaderDetails readerDetails = null;

        try {
            readerDetails = readerService.create(readerRequest);
            //TODO: Key violation here, even though we just created the user and readerDetails...
            readerService.save(readerDetails);
        } catch (Exception e) {
            userService.delete(user.getId());

            throw new ConflictException("Error creating Reader: " + e.getMessage());
        }

        final var newReaderUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(readerDetails.getReaderNumber().toString())
                .build().toUri();

        return ResponseEntity.created(newReaderUri).body(readerViewMapper.toReaderView(readerDetails));
    }

    @Operation(summary = "Updates a reader")
    @RolesAllowed(Role.READER)
    @PatchMapping
    public ResponseEntity<ReaderView> updateReader(@RequestBody UpdateReaderRequest readerRequest, Authentication authentication) {
        User loggedUser = isUserLoggedIn(authentication);
        Optional<ReaderDetails> optReader = this.readerService.findByUsername(loggedUser.getUsername());

        if(optReader.isEmpty()) {
            throw new NotFoundException("User with given authentication not found");
        }

        ReaderDetails reader = optReader.get();

        EditUserRequest editUserRequest = new EditUserRequest();
        editUserRequest.setUsername(readerRequest.getUsername());
        editUserRequest.setPassword(readerRequest.getPassword());
        editUserRequest.setName(readerRequest.getFullName());
        editUserRequest.setUsername(readerRequest.getUsername());

        try {
            reader.applyPatch(readerRequest);
            userService.update(loggedUser.getId(), editUserRequest);
            readerService.save(reader);
        } catch(Exception e) {
            throw new ConflictException("Error updating reader details: " + e.getMessage());
        }

        return ResponseEntity.accepted().body(readerViewMapper.toReaderView(reader));
    }

    @RolesAllowed({Role.READER, Role.ADMIN})
    @Operation(summary = "Gets the lendings of this reader by ISBN")
    @GetMapping(value = "{year}/{seq}/lendings?isbn={isbn}")
    public Iterable<LendingView> getReaderLendingsByIsbn(
            Authentication authentication,
            @PathVariable("year")
            @Parameter(description = "The year of the Reader to find")
            final Integer year,
            @PathVariable("seq")
            @Parameter(description = "The sequencial of the Reader to find")
            final Integer seq,
            @PathVariable("isbn")
            @Parameter(description = "The ISBN of the Book to find")
            final String isbn)
    {
        User loggedUser = isUserLoggedIn(authentication);

        String urlReaderNumber = year + "/" + seq;

        final var urlReaderDetails = readerService.findByReaderNumber(urlReaderNumber)
                .orElseThrow(() -> new NotFoundException(Lending.class, urlReaderNumber));

        if(!(loggedUser instanceof Librarian)){
            //if Librarian is logged in, skip ahead
            Optional<ReaderDetails> loggedReaderDetails = readerService.findByUsername(loggedUser.getUsername());
            if(loggedReaderDetails.isPresent()){
                if(loggedReaderDetails.get().getReaderNumber() != urlReaderDetails.getReaderNumber()){
                    //if logged Reader matches the one associated with the lendings, skip ahead
                    throw new AccessDeniedException("Reader does not have permission to view these lendings");
                }
            }
        }
        return lendingViewMapper.toLendingView(lendingService.listByReaderNumberAndIsbn(urlReaderNumber, isbn));
    }

    private User isUserLoggedIn(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedUsername = userDetails.getUsername();
        Optional<User> loggedUser = this.userService.findByUsername(loggedUsername);
        if(loggedUser.isEmpty())
            throw new AccessDeniedException("User is not logged in");
        return loggedUser.get();
    }

    private class ErrorResponse {
        private String error = "";

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
