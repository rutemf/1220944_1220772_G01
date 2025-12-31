package authors.authors.services;

import authors.authors.model.Bio;
import authors.shared.model.Name;
import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Data
@NoArgsConstructor
public class UpdateAuthorRequest {

    @Setter
    private Long authorNumber;

    @Setter
    private Name name;

    @Setter
    private Bio bio;

    @Nullable
    @Setter
    private String photoURI;

    @Nullable
    @Getter
    @Setter
    private MultipartFile photo;

    public UpdateAuthorRequest(Long authorNumber, Name name, Bio bio, String photoURI) {
        this.authorNumber = authorNumber;
        this.name = name;
        this.bio = bio;
        this.photoURI = photoURI;
    }

}
