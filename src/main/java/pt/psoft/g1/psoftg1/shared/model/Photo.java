package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Getter
    @Lob
    private String photoURI;

    protected Photo (){}
    public Photo (String photoUri){
        setPhotoURI(photoUri);
    }
}

