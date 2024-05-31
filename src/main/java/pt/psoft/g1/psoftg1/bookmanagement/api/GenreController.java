package pt.psoft.g1.psoftg1.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreService;
import pt.psoft.g1.psoftg1.shared.api.ListResponse;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Tag(name = "Genres", description = "Endpoints for managing Genres")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;
    private final GenreViewMapper genreViewMapper;

    @RolesAllowed(Role.LIBRARIAN)
    @ApiResponse(description = "Success",
            responseCode = "200",
            content = { @Content(mediaType = "application/json",
            // Use the `array` property instead of `schema`
            array = @ArraySchema(schema = @Schema(implementation = GenreAvgLendingsView.class))) })
    @GetMapping(value="/avgLendings", params = {"period", "start", "end"})
    public ListResponse<GenreAvgLendingsView> getAverageLendings(
            @RequestParam("period") final String period,
            @RequestParam("start") final String start,
            @RequestParam("end") final String end) {

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Expected format is YYYY-MM-DD");
        }

        if (!period.equalsIgnoreCase("day") &&
                !period.equalsIgnoreCase("week") &&
                !period.equalsIgnoreCase("month") &&
                !period.equalsIgnoreCase("year")) {
            throw new IllegalArgumentException("Possible average periods - day, week, month, year");
        }
        return new ListResponse<>(genreViewMapper.toGenreAvgLendingsView(genreService.getAverageLendings(period, startDate, endDate)));
    }
}
