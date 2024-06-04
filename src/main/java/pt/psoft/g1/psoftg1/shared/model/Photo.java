package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Setter
    @Getter
    @Lob
    private String photoFile;

    protected Photo (){}
    public Photo (Path photoUri){setPhotoFile(photoUri.toString());}
}

