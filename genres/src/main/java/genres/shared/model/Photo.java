package genres.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @NotNull
    @Setter
    @Getter
    private String photoFile;

    public Photo(Path photoPath) {
        setPhotoFile(photoPath.toString());
    }

    protected Photo() { }
}
