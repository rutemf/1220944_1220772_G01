package authors.authors.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@Schema(description = "A Author")
public class AuthorsView {

    @NotNull
    @Schema(description = "Author name")
    private String name;

    @Schema(description = "Author biography")
    private String bio;

    @Setter
    @Getter
    private Map<String, Object> _links = new HashMap<>();


}
