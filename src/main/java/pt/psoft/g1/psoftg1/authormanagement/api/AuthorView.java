package pt.psoft.g1.psoftg1.authormanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "A Lending")
public class AuthorView {

    @NotNull
    private Long AuthorNumber;

    @NotNull
    private String name;

    @NotNull
    private String bio;

}


