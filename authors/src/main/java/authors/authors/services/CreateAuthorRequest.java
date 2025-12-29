package authors.authors.services;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Data
@NoArgsConstructor
@Schema(description = "A DTO for creating a Author")
public class CreateAuthorRequest {

    @NotBlank
    private String name;

    @Nullable
    @Setter
    private String bio;

    @Nullable
    @Setter
    private MultipartFile photo;

    @Nullable
    @Setter
    private String photoURI;

}
