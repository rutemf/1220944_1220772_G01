package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Embeddable;

import static org.springdoc.core.service.GenericResponseService.setDescription;

@Embeddable
public class Description {
    //TODO: Define the size and domain rules to the description of the book
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
