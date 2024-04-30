package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import static org.springdoc.core.service.GenericResponseService.setDescription;

@Embeddable
public class Description {
    //TODO: Define the size and domain rules to the description of the book
    @Nullable
    @Size(min = 1, max = 4096)
    String description;

    public Description(String description) {
        setDescription(description);
    }

    protected Description() {

    }

    private void setDescription(String description) {
        this.description = description;
    }
}
