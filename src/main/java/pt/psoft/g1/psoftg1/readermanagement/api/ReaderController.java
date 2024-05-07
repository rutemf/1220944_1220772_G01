package pt.psoft.g1.psoftg1.readermanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingView;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.usermanagement.api.ListResponse;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.services.CreateUserRequest;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

import java.util.Optional;

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
    public ListResponse<ReaderView> findAll() {
        return new ListResponse<>(readerViewMapper.toReaderView(readerService.findAll()));
    }

    @Operation(summary = "Creates a reader")
    @PutMapping
    public ResponseEntity<Object> createReader(@RequestBody CreateReaderRequest readerRequest) {
        CreateUserRequest userRequest = new CreateUserRequest();

        userRequest.setUsername(readerRequest.getUsername());
        userRequest.setPassword(readerRequest.getPassword());
        userRequest.setRole(Role.READER);

        User user = null;

        try {
            user = userService.create(userRequest);
        } catch(Exception e) {
            ErrorResponse responseBody = new ErrorResponse("Error creating reader: " + e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        ReaderDetails readerDetails = null;

        try {
            readerDetails = readerService.create(readerRequest);
            //Save was done automatically by the demo user repo, so we'll just keep on going.
            //TODO: Maybe change readerService to save the Reader automatically into the repo and handle the error for once, as deleting is quite easy and does not need any error handling
            readerService.save(readerDetails);
        } catch (Exception e) {
            if(user != null) {
                userService.delete(user.getId());
            }

            ErrorResponse responseBody = new ErrorResponse("Error creating reader: " + e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(readerDetails, HttpStatus.CREATED);
    }

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
