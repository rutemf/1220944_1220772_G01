package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.IDBase65GeneratorService;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.nio.file.Paths;

@Getter
@Setter
@Document(collection = "photos")
public class PhotoNoSQL {

    @Transient
    private IDGeneratorService idGeneratorService;

    @Id
    private String id;

    @NotNull
    private String photoFile;

    public PhotoNoSQL(Photo photo) {
        this.id = idGeneratorService.generateId();
        this.photoFile = photo.getPhotoFile();
    }

    // MongoDB
    protected PhotoNoSQL () {}

    public Photo toDomain() {
        return new Photo(Paths.get(photoFile));
    }

    public static PhotoNoSQL fromDomain(Photo domain) {
        return new PhotoNoSQL(domain);
    }
}
