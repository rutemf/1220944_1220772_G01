package pt.psoft.g1.psoftg1.lendingmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.CreateLendingDto;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

import java.nio.file.AccessDeniedException;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

@Tag(name = "Lendings", description = "Endpoints for managing Lendings")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lending")
public class LendingController {

    private final AuthenticationManager authenticationManager;

    private static final String IF_MATCH = "If-Match";

    private static final Logger logger = LoggerFactory.getLogger(LendingController.class);

    private final LendingService service;
    private final ReaderService readerService;
    private final UserService userService;

    private final LendingViewMapper lendingViewMapper;

    @Operation(summary = "Creates a new Lending")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LendingView> create(@Valid @RequestBody final CreateLendingDto resource) {

        final var lending = service.create(resource);

        final var newlendingUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(lending.getLendingNumber().toString())
                .build().toUri();

        return ResponseEntity.created(newlendingUri).eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }

    @Operation(summary = "Gets a specific Lending")
    @GetMapping(value = "/{year}/{sequential}")
    public ResponseEntity<LendingView> findByLendingNumber(
            Authentication authentication,
            @PathVariable("year")
                @Parameter(description = "The year of the Lending to find")
                final Integer year,
            @PathVariable("sequential")
                @Parameter(description = "The sequencial of the Lending to find")
                final Integer sequential) throws AccessDeniedException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<User> optUser = this.userService.findByUsername(username);
        if(optUser.isEmpty()) {
            throw new AccessDeniedException("User is not logged in");
        }

        ReaderDetails readerDetails = null;
        //TODO: Get readerDetails from the username

        String ln = year + "/" + sequential;

        final var lending = service.findByLendingNumber(ln)
                .orElseThrow(() -> new NotFoundException(Lending.class, ln));

        return ResponseEntity.ok()
                .eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }

}
