package readers.shared.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

@Getter
@MappedSuperclass
public abstract class EntityWithPhoto {

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    protected Photo photo;

    public void setPhoto(String photoUri) {
        this.setPhotoInternal(photoUri);
    }

    protected void setPhotoInternal(String photoURI) {
        if (photoURI == null) {
            this.photo = null;
        } else {
            try {
                this.photo = new Photo(Path.of(photoURI));
            } catch (InvalidPathException e) {
                this.photo = null;
            }
        }
    }
}
