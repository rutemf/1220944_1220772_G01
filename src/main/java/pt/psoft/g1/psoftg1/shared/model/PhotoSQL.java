package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.IDBase65GeneratorService;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.nio.file.Paths;

@Entity
@Getter
@Setter
@Table(name = "photo")
public class PhotoSQL {

    @Transient
    private IDGeneratorService idGeneratorService;

    @Id
    private String id;

    @NotNull
    private String photoFile;

    public PhotoSQL(Photo photo) {
        this.id = idGeneratorService.generateId();
        this.photoFile = photo.getPhotoFile();
    }

    // JPA
    protected PhotoSQL (){}

    public Photo toDomain() {
        return new Photo(Paths.get(photoFile));
    }

    public static PhotoSQL fromDomain(Photo domain) {
        return new PhotoSQL(domain);
    }
}

