package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;


//TODO: Running the app crashes because it says it should not have the @Id notation, which it doesn't...
@Embeddable
public class ReaderNumber {
    @Getter
    @Column(nullable = false, updatable = false)
    private int year;
    @Getter
    @Column(nullable = false, updatable = false)
    private int number;

    @Override
    public String toString() {
        return this.year + "/" + this.number;
    }

    public ReaderNumber(int year, int number) {
        this.year = year;
        this.number = number;
    }

    protected ReaderNumber() {}
}
