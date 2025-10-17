package pt.psoft.g1.psoftg1.authormanagement.model;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Name;

import java.io.Serializable;

@Getter
@Setter
public class Author extends EntityWithPhoto implements Serializable {

    private Long authorNumber;
    private Name name;
    private Bio bio;

    public Author(Long authorNumber, Name name, Bio bio) {
        this.authorNumber = authorNumber;
        this.name = name;
        this.bio = bio;
    }

    protected Author() { }

    public void applyPatch(final UpdateAuthorRequest request) {
        if (request.getName() != null) {
            setName(new Name(request.getName()));
        }

        if (request.getBio() != null) {
            setBio(new Bio(request.getBio()));
        }

        if (request.getPhotoURI() != null) {
            setPhotoInternal(request.getPhotoURI());
        }
    }

    public void removePhoto(long desiredVersion) {
        setPhotoInternal(null);
    }
}

