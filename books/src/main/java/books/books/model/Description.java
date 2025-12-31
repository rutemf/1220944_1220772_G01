package books.books.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import books.shared.model.StringUtilsCustom;

@Embeddable
public class Description {

    @Transient
    private final int DESC_MAX_LENGTH = 4096;

    @Size(max = DESC_MAX_LENGTH)
    @Column(length = DESC_MAX_LENGTH)
    private String description;

    public Description(String description) {
        setDescription(description);
    }

    protected Description() { }

    public void setDescription(@Nullable String description) {
        if (description == null || description.isBlank()) {
            this.description = null;
        } else if (description.length() > DESC_MAX_LENGTH) {
            throw new IllegalArgumentException("Description has a maximum of 4096 characters");
        } else {
            this.description = StringUtilsCustom.sanitizeHtml(description);
        }
    }

    @Override
    public String toString() {
        return this.description;
    }
}
