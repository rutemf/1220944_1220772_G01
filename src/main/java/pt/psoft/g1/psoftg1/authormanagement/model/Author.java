package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Value;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    @Getter
    private Long authorNumber;

    @Version
    private long version;

    @Embedded
    private Name name;

    @Nullable
    @Getter
    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Embedded
    private Bio bio;

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setBio(String bio) {
        this.bio = new Bio(bio);
    }

    private void setPhotoInternal(String photo) {
        if (photo == null) {
            this.photo = null;
            return;
        }

        this.photo = new Photo(Paths.get(photo));
    }

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return authorNumber;
    }

    public Author(String name, String bio, String photoURI) {
        setName(name);
        setBio(bio);
        if (photoURI != null) {
            try {
                //If the Path object instantiation succeeds, it means that we have a valid Path
                this.photo = new Photo(Paths.get(photoURI));
            } catch (InvalidPathException e) {
                //For some reason it failed, let's set to null to avoid invalid references to photos
                this.photo = null;
            }
        } else {
            this.photo = null;
        }

    }

    public void setPhoto(String photo) {
        setPhotoInternal(photo);
    }

    protected Author() {
        // got ORM only
    }


    public void applyPatch(final long desiredVersion, final UpdateAuthorRequest request) {

        if (this.version != desiredVersion) {
            throw new StaleObjectStateException("Object was already modified by another user", this.authorNumber);
        }
        if (request.getName() != null) {
            setName(request.getName());
        }

        if (request.getBio() != null) {
            setBio(request.getBio());
        }
        if (request.getPhotoURI() != null) {
            try {
                setPhotoInternal(request.getPhotoURI());
            } catch (InvalidPathException ignored) {
            }
        } else {
            setPhotoInternal(null);
        }
    }


    public String getName() {
        return this.name.toString();
    }

    public String getBio() {
        return this.bio.toString();
    }
}

