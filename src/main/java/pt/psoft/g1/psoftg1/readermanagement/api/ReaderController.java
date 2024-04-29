package pt.psoft.g1.psoftg1.readermanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.services.CreateUserRequest;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

import java.util.List;

@Tag(name = "Readers", description = "Endpoints to manage readers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readers")
class ReaderController {
    private final ReaderService readerService;
    private final UserService userService;
    private final ReaderViewMapper readerViewMapper;

    @Operation(summary = "Gets all readers")
    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(mediaType = "application/json",
            // Use the `array` property instead of `schema`
            array = @ArraySchema(schema = @Schema(implementation = ReaderView.class))) })
    @GetMapping
    public List<ReaderView> findAll() {
        return readerViewMapper.toReaderView(readerService.findAll());
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
        } catch(ConflictException e) {
            ErrorResponse responseBody = new ErrorResponse("Error creating reader: " + e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        Reader reader = null;

        try {
            reader = readerService.create(readerRequest);
            //Save was done automatically by the demo user repo, so we'll just keep on going.
            //TODO: Maybe change readerService to save the Reader automatically into the repo and handle the error for once, as deleting is quite easy and does not need any error handling
            readerService.save(reader);
        } catch (Exception e) {
            if(user != null) {
                userService.delete(user.getId());
            }

            ErrorResponse responseBody = new ErrorResponse("Error creating reader: " + e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(reader, HttpStatus.CREATED);
    }

    private class ErrorResponse {
        private String error = "";

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
