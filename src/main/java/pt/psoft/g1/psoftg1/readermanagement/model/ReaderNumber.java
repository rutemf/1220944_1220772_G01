package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class ReaderNumber implements Serializable {
    private int year;
    private int number;

    @Override
    public String toString(){
        return this.year + "/" + this.number;
    };
}
