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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingView;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.shared.api.ListResponse;
import pt.psoft.g1.psoftg1.shared.api.UploadFileResponse;
import pt.psoft.g1.psoftg1.shared.model.FileUtils;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Readers", description = "Endpoints to manage readers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readers")
class ReaderController {
    private final ReaderService readerService;
    private final UserService userService;
    private final ReaderViewMapper readerViewMapper;
    private final LendingService lendingService;
    private final LendingViewMapper lendingViewMapper;
    private final ConcurrencyService concurrencyService;
    private final FileStorageService fileStorageService;


    private static final String IF_MATCH = "If-Match";

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
    @Operation(summary = "Gets reader by number")
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
    @GetMapping(params = "name")
    public ListResponse<ReaderView> findByReaderName(@RequestParam("name") final String name) {
        /*SearchUsersQuery query = new SearchUsersQuery();
        query.setFullName(name);
        query.setUseNameAsString(true);
        Page page = new Page(1, 20);
        List<User> userList = this.userService.searchUsers(page, query);*/
        List<User> userList = this.userService.findByName(name);
        List<ReaderDetails> readerDetailsList = new ArrayList<>();

        for(User user : userList) {
            Optional<ReaderDetails> readerDetails = this.readerService.findByUsername(user.getUsername());
            if(readerDetails.isPresent()) {
                readerDetailsList.add(readerDetails.get());
            }
        }

        if(readerDetailsList.isEmpty()) {
            throw new NotFoundException("Could not find reader with name: " + name);
        }

        return new ListResponse<>(readerViewMapper.toReaderView(readerDetailsList));
    }

    @Operation(summary = "Creates a reader")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReaderView> createReader(@RequestBody CreateReaderRequest readerRequest, @RequestParam(value="photo", required=false) MultipartFile file, Authentication authentication) throws URISyntaxException, URISyntaxException {
        //TODO: Find another way to just call create instead of creating + updating
        ReaderDetails readerDetails = readerService.create(readerRequest);

        String readerNumber = readerDetails.getReaderNumber().toString();

        UploadFileResponse up = null;

        //Parameter has a valid photo file
        if(file != null) {
            //Reader registration continues even though the photo doesn't go through
            //TODO: Above condition must be checked with teachers
            try {
                up = FileUtils.doUploadFile(fileStorageService, readerNumber, file);
            } catch(Exception e) {
                //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            URI photoUri = null;
            if(up != null) {
                photoUri = new URI(up.getFileDownloadUri());

                UpdateReaderRequest request = new UpdateReaderRequest();
                request.setPhotoURI(photoUri.toString());
                readerDetails.applyPatch(readerDetails.getVersion(), request);

                User loggedUser = isUserLoggedIn(authentication);
                readerService.update(loggedUser.getId(), request, readerDetails.getVersion());
            }
        }

        final var newReaderUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(readerDetails.getReaderNumber().toString())
                .build().toUri();

        return ResponseEntity.created(newReaderUri)
                .eTag(Long.toString(readerDetails.getVersion()))
                .body(readerViewMapper.toReaderView(readerDetails));
    }

    @Operation(summary = "Updates a reader")
    @RolesAllowed(Role.READER)
    @PatchMapping
    public ResponseEntity<ReaderView> updateReader(
            @Valid @RequestBody UpdateReaderRequest readerRequest,
            Authentication authentication,
            final WebRequest request) {

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        User loggedUser = isUserLoggedIn(authentication);
        ReaderDetails readerDetails = readerService
                .update(loggedUser.getId(), readerRequest, concurrencyService.getVersionFromIfMatchHeader(ifMatchValue));

        return ResponseEntity.ok()
                .eTag(Long.toString(readerDetails.getVersion()))
                .body(readerViewMapper.toReaderView(readerDetails));
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

    //TODO: Modify the mapping accordingly and apply the min top (static of dynamic)
    @GetMapping("top5")
    public ListResponse<ReaderView> getTop() {
        return new ListResponse<ReaderView>(readerViewMapper.toReaderView(readerService.findTopReaders(5)));
    }

    private User isUserLoggedIn(Authentication authentication){
        if (authentication == null) {
            throw new AccessDeniedException("User is not logged in");
        }
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
