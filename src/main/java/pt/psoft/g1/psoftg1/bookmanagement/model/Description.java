package pt.psoft.g1.psoftg1.bookmanagement.model;

import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

import java.io.Serializable;

public class Description implements Serializable {

    private String description;

    public Description(String description) {
        setDescription(description);
    }

    protected Description() { }

    public void setDescription(String description) {
        int DESC_MAX_LENGTH = 4096;

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
