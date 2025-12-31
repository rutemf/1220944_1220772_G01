package books.authors.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;
import books.exceptions.ConflictException;
import books.shared.model.EntityWithPhoto;
import books.shared.model.Name;

@Entity
public class Author extends EntityWithPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    @Getter
    private long authorNumber;

    @Version
    private long version;

    @Embedded
    private Name name;

    @Embedded
    private Bio bio;

    public Author(String name, String bio, String photoURI) {
        setName(name);
        setBio(bio);
        setPhotoInternal(photoURI);
    }

    protected Author() { }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setBio(String bio) {
        this.bio = new Bio(bio);
    }

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return authorNumber;
    }

    public String getName() {
        return this.name.toString();
    }
}