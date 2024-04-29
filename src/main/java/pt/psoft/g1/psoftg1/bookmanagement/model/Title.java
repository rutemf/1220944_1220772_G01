package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class Title {
    //TODO: Confirmar os valores maximos e minimos do title
    @Size(min = 2, max = 50)
    String title;

    protected Title() {

    }

    public int size() {
        return title.length();
    }

    public Title(String title) {
        setTitle(title);
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
