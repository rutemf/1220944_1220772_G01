package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;

import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    private Long authorNumber;

    @Setter
    @Embedded
    @Getter
    Name name;

    @OneToOne(fetch = FetchType.LAZY)
    Photo photo;

    @Embedded
    Bio bio;

    private void setName(String name) {
        this.name = new Name(name);
    }

    private void setBio(String bio) {
        this.bio = new Bio(bio);
    }

    private void setPhoto(byte[] photo) {
        this.photo = new Photo(photo);
    }
    public Author(String name, String bio, byte[] photo) {
        setName(name);
        setBio(bio);
        setPhoto(photo);

    }

    protected Author() {
        // got ORM only
    }

    public void applyPatch(UpdateAuthorRequest request) {
        String name = request.getName();
        String bio = request.getBio();
        byte[] photo = request.getPhoto();

        if (name != null) {
            setName(name);
        }

        if (bio != null) {
            setBio(bio);
        }

        if (photo != null) {
            setPhoto(photo);
        }
    }
}

