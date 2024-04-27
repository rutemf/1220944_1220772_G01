package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LendingNumber implements Serializable {
    @Column(name = "YEAR")
    private int year;
    @Column(name = "NUMBER")
    private int number;

    @Override
    public String toString(){
        return this.year + "/" + this.number;
    };
}
