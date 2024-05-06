package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pt.psoft.g1.psoftg1.shared.model.StringUtils;

@Embeddable
public class Title {
    //TODO: Confirmar os valores maximos e minimos do title
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 64)
    @Column(name="TITLE") // , length = x
    String title;

    protected Title() {}

    public Title(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        if (!StringUtils.startsOrEndsInWhiteSpace(title)) {
            throw new IllegalArgumentException("Invalid title: " + title);
        }
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
