package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class Title {
    //TODO: Confirmar os valores maximos e minimos do title
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 64)
    String title;

    protected Title() {}

    public int size() {
        return title.length();
    }

    public Title(String title) {
        setTitle(title);
    }

    private boolean verifierSpaces(String title) {
        return !title.startsWith(" ") && !title.endsWith(" ");
    }

    private void setTitle(String title) {
        if (!verifierSpaces(title)) {
            throw new IllegalArgumentException("Invalid title: " + title);
        }
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
