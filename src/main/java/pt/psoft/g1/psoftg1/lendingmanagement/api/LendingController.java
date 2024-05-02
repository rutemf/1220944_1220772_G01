package pt.psoft.g1.psoftg1.lendingmanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.CreateLendingDto;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;

@Tag(name = "Lendings", description = "Endpoints for managing Lendings")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lending")
public class LendingController {

    private static final String IF_MATCH = "If-Match";

    private static final Logger logger = LoggerFactory.getLogger(LendingController.class);

    private final LendingService service;

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
            @PathVariable("year")
                @Parameter(description = "The year of the Lending to find")
                final Integer year,
            @PathVariable("sequential")
                @Parameter(description = "The sequencial of the Lending to find")
                final Integer sequential) {

        String ln = year + "/" + sequential;

        final var lending = service.findByLendingNumber(ln)
                .orElseThrow(() -> new NotFoundException(Lending.class, ln));

        return ResponseEntity.ok()
                .eTag(Long.toString(lending.getVersion()))
                .body(lendingViewMapper.toLendingView(lending));
    }
    
}
