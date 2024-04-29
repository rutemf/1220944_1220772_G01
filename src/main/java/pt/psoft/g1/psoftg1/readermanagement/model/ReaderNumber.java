package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;


@Embeddable
public class ReaderNumber implements Serializable {
    @Getter
    private int year;
    @Getter
    private int number;

    @Override
    public String toString(){
        return this.year + "/" + this.number;
    };

    public ReaderNumber(int year, int number) {
        this.year = year;
        this.number = number;
    }

    protected ReaderNumber() {}
}
