package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;
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

/*
    @OneToOne(fetch = FetchType.LAZY)
    private Photo photo;
*/

    @Embedded
    @Getter
    private Bio bio;

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setBio(String bio) {
        this.bio = new Bio(bio);
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


    public void applyPatch(final long desiredVersion, final String name, final String bio) {

        if (this.version != desiredVersion) {
            throw new StaleObjectStateException("Object was already modified by another user", this.authorNumber);
        }
        if (name != null) {
            setName(name);
        }

        if (bio != null) {
            setBio(bio);
        }
    }
}

