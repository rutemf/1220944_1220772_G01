package authors.authors.model;

import authors.exceptions.ConflictException;
import authors.shared.model.EntityWithPhoto;
import authors.shared.model.Name;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;

import java.util.Objects;

@Entity
@Table(
        name = "Author",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_author_name", columnNames = {"AUTHOR_NUMBER"})
        }
)
public class Author extends EntityWithPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    @Getter
    private Long authorNumber;

    @Version
    @Getter
    private Long version;

    @Embedded
    @NotNull
    Name name;

    @Embedded
    Bio bio;

    protected Author() {}

    public Author(String name, String bio, String photoURI) {
        setName(name);

        if (bio != null) {
            setBio(bio);
        }

        setPhotoInternal(photoURI);
    }

    private void setName(String name) {
        this.name = new Name(name);
    }

    private void setBio(String bio) {
        this.bio = new Bio(bio);
    }

    public String getName() {
        return name == null ? null : name.toString();
    }

    public String getBio() {
        return bio == null ? null : bio.toString();
    }

    public void removePhoto(long desiredVersion) {
        if (!Objects.equals(this.version, desiredVersion)) {
            throw new ConflictException(
                    "Provided version does not match latest version of this object"
            );
        }
        setPhotoInternal(null);
    }

    public void applyPatch(final Long desiredVersion,
                           final String name,
                           final String bio,
                           final String photoURI) {

        if (!Objects.equals(this.version, desiredVersion)) {
            throw new StaleObjectStateException(
                    "Object was already modified by another user",
                    this.authorNumber
            );
        }

        if (name != null) {
            setName(name);
        }

        if (bio != null) {
            setBio(bio);
        }

        if (photoURI != null) {
            setPhotoInternal(photoURI);
        }
    }
}
