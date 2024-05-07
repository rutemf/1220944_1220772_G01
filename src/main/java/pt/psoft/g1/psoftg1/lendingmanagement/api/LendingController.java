package pt.psoft.g1.psoftg1.lendingmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.CreateLendingRequest;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.lendingmanagement.services.SetLendingReturnedDto;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

import java.util.Optional;

@Tag(name = "Lendings", description = "Endpoints for managing Lendings")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lending")
public class LendingController {

    private static final String IF_MATCH = "If-Match";

    private static final Logger logger = LoggerFactory.getLogger(LendingController.class);

    private final LendingService lendingService;
    private final ReaderService readerService;
    private final UserService userService;

    private final LendingViewMapper lendingViewMapper;

    @RolesAllowed(Role.LIBRARIAN)
    @Operation(summary = "Creates a new Lending")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LendingView> create(@Valid @RequestBody final CreateLendingRequest resource) {

        final var lending = lendingService.create(resource);

        final var newlendingUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(lending.getLendingNumber())
                .build().toUri();

        return ResponseEntity.created(newlendingUri)
                .eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }

    @Operation(summary = "Gets a specific Lending")
    @GetMapping(value = "/{year}/{seq}")
    public ResponseEntity<LendingView> findByLendingNumber(
            Authentication authentication,
            @PathVariable("year")
                @Parameter(description = "The year of the Lending to find")
                final Integer year,
            @PathVariable("seq")
                @Parameter(description = "The sequencial of the Lending to find")
                final Integer seq)
    {
        User loggedUser = isUserLoggedIn(authentication);

        String ln = year + "/" + seq;
        final var lending = lendingService.findByLendingNumber(ln)
                .orElseThrow(() -> new NotFoundException(Lending.class, ln));

        if(!(loggedUser instanceof Librarian)){
            //if Librarian is logged in, skip ahead
            Optional<ReaderDetails> loggedReaderDetails = readerService.findByUsername(loggedUser.getUsername());
            if(loggedReaderDetails.isPresent()){
                if(loggedReaderDetails.get().getReaderNumber() != lending.getReaderDetails().getReaderNumber()){
                    //if logged Reader matches the one associated with the lending, skip ahead
                    throw new AccessDeniedException("Reader does not have permission to view this lending");
                }
            }
        }

        return ResponseEntity.ok()
                .eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }

    @RolesAllowed(Role.READER)
    @Operation(summary = "Sets a lending as returned")
    @PatchMapping(value = "/{year}/{seq}")
    public ResponseEntity<LendingView> setLendingReturned(
            final WebRequest request,
            final Authentication authentication,
            @PathVariable("year")
                @Parameter(description = "The year component of the Lending to find")
                final Integer year,
            @PathVariable("seq")
                @Parameter(description = "The sequential component of the Lending to find")
                final Integer seq,
            @Valid @RequestBody final SetLendingReturnedDto resource)
    {
        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }
        User loggedUser = isUserLoggedIn(authentication);

        String ln = year + "/" + seq;
        final var maybeLending = lendingService.findByLendingNumber(ln)
                .orElseThrow(() -> new NotFoundException(Lending.class, ln));

        Optional<ReaderDetails> loggedReaderDetails = readerService.findByUsername(loggedUser.getUsername());
        if(loggedReaderDetails.isPresent()){
            if(loggedReaderDetails.get().getReaderNumber() != maybeLending.getReaderDetails().getReaderNumber()){
                //if logged Reader matches the one associated with the lending, skip ahead
                throw new AccessDeniedException("Reader does not have permission to edit this lending");
            }
        }

        final var lending = lendingService.setReturned(ln, resource, getVersionFromIfMatchHeader(ifMatchValue));

        return ResponseEntity.ok()
                .eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }

    private User isUserLoggedIn(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedUsername = userDetails.getUsername();
        Optional<User> loggedUser = this.userService.findByUsername(loggedUsername);
        if(loggedUser.isEmpty())
            throw new AccessDeniedException("User is not logged in");
        return loggedUser.get();
    }

    private Long getVersionFromIfMatchHeader(final String ifMatchHeader) {
        if (ifMatchHeader.startsWith("\"")) {
            return Long.parseLong(ifMatchHeader.substring(1, ifMatchHeader.length() - 1));
        }
        return Long.parseLong(ifMatchHeader);
    }
}
