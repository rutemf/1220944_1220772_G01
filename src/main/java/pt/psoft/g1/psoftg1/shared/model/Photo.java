package pt.psoft.g1.psoftg1.shared.model;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class Photo {

    private String photoFile;

    public Photo (Path photoPath) {
        this.photoFile = photoPath.toString();
    }
}

