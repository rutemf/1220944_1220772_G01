package pt.psoft.g1.psoftg1.shared.dataschema;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.nio.file.Paths;

@Getter
@Setter
@Document(collection = "photo")
public class PhotoNoSQL {

    @Id
    private String id;

    @NotNull
    private String photoFile;

    public PhotoNoSQL(Photo photo) {
        this.id = IDGeneratorService.generateIdNoSQL();
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
