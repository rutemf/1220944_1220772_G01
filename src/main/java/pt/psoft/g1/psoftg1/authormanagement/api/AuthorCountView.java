package pt.psoft.g1.psoftg1.authormanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Authors which have the most lent books")
public class AuthorCountView {
    @NotNull
    private AuthorView authorView;

}


