package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

import java.nio.file.Paths;

@Getter
@Setter
@Document(collection = "photos")
public class PhotoNoSQL {

    @Id
    private String id;

    @NotNull
    private String photoFile;

    public PhotoNoSQL(Photo photo) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdNoSQL();
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
