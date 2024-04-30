package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private byte[] photo;

    @OneToOne(fetch=FetchType.LAZY, mappedBy="photo")
    private Author author;
    protected Photo (){}
    public Photo (byte[] photoBytes){
        this.photo= photoBytes;
    }
}

