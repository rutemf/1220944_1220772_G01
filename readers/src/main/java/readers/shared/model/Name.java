package readers.shared.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Embeddable
public class Name {

    @NotNull
    @NotBlank
    @Column(name = "NAME", length = 150)
    private String name;

    public Name(String name) {
        setName(name);
    }

    protected Name() { }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank, nor only white spaces");
        }

        if (!StringUtilsCustom.isAlphanumeric(name)) {
            throw new IllegalArgumentException("Name can only contain alphanumeric characters");
        }

        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
