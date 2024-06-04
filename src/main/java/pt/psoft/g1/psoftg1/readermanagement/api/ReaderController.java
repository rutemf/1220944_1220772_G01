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
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.photo.max_size}")
    private long photoMaxSize;

    private final String[] validImageFormats = {"image/png", "image/jpeg"};

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

    /*@GetMapping("/{year}/{seq}/photo")
    public ResponseEntity<byte[]> getReaderPhoto(@PathVariable("year") final Long year, @PathVariable("seq") final Long seq) {
        return
    }*/

    @RolesAllowed(Role.LIBRARIAN)
    @GetMapping(params = "name")
    public ListResponse<ReaderView> findByReaderName(@RequestParam("name") final String name) {
        /*SearchUsersQuery query = new SearchUsersQuery();
        query.setFullName(name);
        query.setUseNameAsString(true);
        Page page = new Page(1, 20);
        List<User> userList = this.userService.searchUsers(page, query);*/
        //List<User> userList = this.userService.findByName(name);
        List<User> userList = this.userService.findByNameLike(name);
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

    @Operation(summary= "Gets a reader photo")
    @GetMapping("/{year}/{seq}/photo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getSpecificReaderPhoto(@PathVariable("year")
                                                     @Parameter(description = "The year of the Reader to find")
                                                     final Integer year,
                                                 @PathVariable("seq")
                                                     @Parameter(description = "The sequencial of the Reader to find")
                                                     final Integer seq) {
        Optional<ReaderDetails> optReaderDetails = readerService.findByReaderNumber(year + "/" + seq);
        if(optReaderDetails.isEmpty()) {
            throw new AccessDeniedException("A reader could not be found with provided ID");
        }

        ReaderDetails readerDetails = optReaderDetails.get();
        String photoPathString = uploadDir + readerDetails.getPhoto().getPhotoFile();
        Path photoPath = Paths.get(photoPathString);
        String fileFormat = photoPathString.split("\\.")[1];
        byte[] image = null;
        try {
            image = Files.readAllBytes(photoPath);
        } catch(IOException e) {
            throw new NotFoundException("Could not get reader photo");
        }

        return ResponseEntity.ok().contentType(fileFormat.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG).body(image);
    }

    @Operation(summary= "Gets a reader photo")
    @GetMapping("/photo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getReaderOwnPhoto(Authentication authentication) {

        User loggedUser = userService.getAuthenticatedUser(authentication);

        Optional<ReaderDetails> optReaderDetails = readerService.findByUsername(loggedUser.getUsername());
        if(optReaderDetails.isEmpty()) {
            throw new AccessDeniedException("Could not find a valid reader from current auth");
        }

        ReaderDetails readerDetails = optReaderDetails.get();
        String photoPathString = uploadDir + readerDetails.getPhoto().getPhotoFile();
        Path photoPath = Paths.get(photoPathString);
        String fileFormat = photoPathString.split("\\.")[1];
        byte[] image = null;
        try {
            image = Files.readAllBytes(photoPath);
        } catch(IOException e) {
            throw new NotFoundException("Could not get reader photo");
        }

        return ResponseEntity.ok().contentType(fileFormat.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG).body(image);
    }

    @Operation(summary = "Creates a reader")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReaderView> createReader(@Valid CreateReaderRequest readerRequest) throws ValidationException {
        UploadFileResponse up = null;

        //Guarantee that the client doesn't provide a link on the body, null = no photo or error
        readerRequest.setPhotoURI(null);
        MultipartFile file = readerRequest.getPhoto();

        String fileName = this.getRequestPhoto(file);

        if (fileName != null) {
            readerRequest.setPhotoURI(fileName);
        }

        ReaderDetails readerDetails = readerService.create(readerRequest);

        final var newReaderUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(readerDetails.getReaderNumber().toString())
                .build().toUri();

        return ResponseEntity.created(newReaderUri)
                .eTag(Long.toString(readerDetails.getVersion()))
                .body(readerViewMapper.toReaderView(readerDetails));
    }

    //Request for testing purposes. TODO: DO NOT DELETE
//    @Operation(summary = "...")
//    @PostMapping("/photo")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<String> uploadPhoto(@Valid TestBody body) throws ValidationException {
//        System.out.println("Got fullname from body: " + body.getFullName());
//        UploadFileResponse up = null;
//
//        System.out.println("Got age #1: " + body.getAges().get(0));
//        System.out.println("Got age #2: " + body.getAges().get(1));
//        System.out.println("Got age #3: " + body.getAges().get(2));
//
//        System.out.println("File content type: " + body.getPhoto().getSize());
//
//        String linkValue = "NO PHOTO URI";
//
//        MultipartFile file = body.getPhoto();
//
//        long fileSize = file.getSize();
//
//        if(fileSize > photoMaxSize) {
//            throw new ValidationException("Attached photo can't be bigger than " + photoMaxSize + " bytes");
//        }
//
//
//
//        int formatIndex = -1;
//        String fileContentHeader = file.getContentType();
//
//        if(fileContentHeader == null) {
//            throw new ValidationException("Unknown file content header");
//        }
//
//        for(int i = 0; i < validImageFormats.length; i++) {
//            if(!fileContentHeader.equals(validImageFormats[i])) {
//                continue;
//            }
//
//            formatIndex = i;
//            break;
//        }
//
//        if(formatIndex == -1) {
//            throw new ValidationException("Images can only be png or jpeg");
//        }
//
//        /*if(file != null) {
//            String photoUUID = UUID.randomUUID().toString();
//
//            try {
//                up = FileUtils.doUploadFile(fileStorageService, photoUUID, file);
//            } catch (Exception e) {
//                //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            if (up != null) {
//                URI photoUri = new URI(up.getFileDownloadUri());
//                linkValue = photoUri.toString();
//            }
//        } else {
//            linkValue = "NO PHOTO ATTACHED";
//        }*/
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    //TODO: Meter foto no UpdateReaderRequest e acabar o método do update. Depois fazer os testes unitários do reader
    @Operation(summary = "Updates a reader")
    @RolesAllowed(Role.READER)
    @PatchMapping
    public ResponseEntity<ReaderView> updateReader(
            @Valid UpdateReaderRequest readerRequest,
            Authentication authentication,
            final WebRequest request) {

        final String ifMatchValue = request.getHeader(IF_MATCH);
        if (ifMatchValue == null || ifMatchValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must issue a conditional PATCH using 'if-match'");
        }

        MultipartFile file = readerRequest.getPhoto();

        String fileName = this.getRequestPhoto(file);

        if (fileName != null) {
            readerRequest.setPhotoURI(fileName);
        }

        User loggedUser = userService.getAuthenticatedUser(authentication);
        ReaderDetails readerDetails = readerService
                .update(loggedUser.getId(), readerRequest, concurrencyService.getVersionFromIfMatchHeader(ifMatchValue));

        return ResponseEntity.ok()
                .eTag(Long.toString(readerDetails.getVersion()))
                .body(readerViewMapper.toReaderView(readerDetails));
    }

    //Returns the string of the fileName of the file (UUID.FILE_FORMAT) stored in the uploads folder | null for error or no photo
    private String getRequestPhoto(MultipartFile file) {
        UploadFileResponse up = null;
        if(file != null) {
            if(file.getSize() > photoMaxSize) {
                throw new ValidationException("Attached photo can't be bigger than " + photoMaxSize + " bytes");
            }

            int formatIndex = -1;
            String fileContentHeader = file.getContentType();

            if(fileContentHeader == null) {
                throw new ValidationException("Unknown file content header");
            }

            for(int i = 0; i < validImageFormats.length; i++) {
                if(!fileContentHeader.equals(validImageFormats[i])) {
                    continue;
                }

                formatIndex = i;
                break;
            }

            if(formatIndex == -1) {
                throw new ValidationException("Images can only be png or jpeg");
            }

            String photoUUID = UUID.randomUUID().toString();

            try {
                up = FileUtils.doUploadFile(fileStorageService, photoUUID, file);
            } catch (Exception e) {
                return null;
                //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String fileFormat = validImageFormats[formatIndex].split("/")[1];
            return photoUUID+"."+fileFormat;
        }

        return null;
    }

    @Operation(summary = "Gets the lendings of this reader by ISBN")
    @GetMapping(value = "/{year}/{seq}/lendings", params = {"isbn"})
    public List<LendingView> getReaderLendingsByIsbn(
            Authentication authentication,
            @PathVariable("year")
            @Parameter(description = "The year of the Reader to find")
            final Integer year,
            @PathVariable("seq")
            @Parameter(description = "The sequencial of the Reader to find")
            final Integer seq,
            @RequestParam("isbn")
            @Parameter(description = "The ISBN of the Book to find")
            final String isbn)
    {
        User loggedUser = userService.getAuthenticatedUser(authentication);

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
    @GetMapping("/top5")
    public ListResponse<ReaderView> getTop() {
        return new ListResponse<>(readerViewMapper.toReaderView(readerService.findTopReaders(5)));
    }

}
