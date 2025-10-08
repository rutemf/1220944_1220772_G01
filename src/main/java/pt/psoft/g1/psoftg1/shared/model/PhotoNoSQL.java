package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class PhotoNoSQL {
    @Id
    private String id;

    @NotNull
    private String photoFile;

    public PhotoNoSQL (Path photoPath) {
        this.photoFile = photoPath.toString();
    }

    // MongoDB
    protected PhotoNoSQL (){}
}
