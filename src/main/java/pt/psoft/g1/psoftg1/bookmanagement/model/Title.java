package pt.psoft.g1.psoftg1.bookmanagement.model;

import java.io.Serializable;

public class Title implements Serializable {

    private String title;

    public Title(String title) {
        setTitle(title);
    }

    protected Title() { }

    public void setTitle(String title) {
        int TITLE_MAX_LENGTH = 128;

        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }

        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        if (title.length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException("Title has a maximum of " + TITLE_MAX_LENGTH + " characters");
        }

        this.title = title.strip();
    }

    @Override
    public String toString() {
        return this.title;
    }
}