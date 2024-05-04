package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.model.Name;


@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    private Long authorNumber;


    @Version
    private long version;

    @Embedded
    @Getter
    private Name name;

    @OneToOne(fetch = FetchType.LAZY)
    private Photo photo;

    @Embedded
    private Bio bio;

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setBio(String bio) {
        this.bio = new Bio(bio);
    }

    public void setPhoto(byte[] photo) {
        this.photo = new Photo(photo);
    }

    public Long getVersion() { return version;}

    public Long getId() { return authorNumber;}

    public Author(String name, String bio) {
        setName(name);
        setBio(bio);
    }

    protected Author() {
        // got ORM only
    }


    public void applyPatch(final long desiredVersion, UpdateAuthorRequest request) {
        String name = request.getName();
        String bio = request.getBio();
        byte[] photo = request.getPhoto();

        if (this.version != desiredVersion) {
            throw new StaleObjectStateException("Object was already modified by another user", this.authorNumber);
        }
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

