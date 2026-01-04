package readers.readers.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import readers.readers.services.CreateReaderRequest;
import readers.readers.services.ReaderService;
import readers.readers.services.ReaderView;

@Tag(name = "Reader")
@RestController
@RequestMapping(path = "/api/readers")
@RequiredArgsConstructor
public class ReaderApi {
    private final ReaderService readerService;
    private final ReaderViewMapper readerViewMapper;

    @PostMapping
    public ReaderView create(@RequestBody @Valid final CreateReaderRequest request) {
        final var reader = readerService.create(request);
        return readerViewMapper.toReaderView(reader, request.getEmail(), request.getFullName());
    }

    @GetMapping("/{readerNumber}")
    public ReaderView getByReaderNumber(@PathVariable String readerNumber) {
        final String getReaderNumber = "2025/" + readerNumber;
        final var reader = readerService.findByReaderNumber(getReaderNumber);
        return readerViewMapper.toReaderView(reader, null, null);
    }
}
