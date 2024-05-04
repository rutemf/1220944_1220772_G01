package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class Description {
    @Size(min = 1, max = 4096)
    @Column(length = 4096)
    String description;

    public Description(String description) {
        setDescription(description);
    }

    protected Description() {}

    private void setDescription(@Nullable String description) {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        } else {
            this.description = null;
        }
    }

    public String toString() {
        return this.description;
    }
}
