package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class LendingNumber implements Serializable {
    @Column(name = "LENDING_NUMBER")
    private String lendingNumber;

    @Column(name = "YEAR")
    private int year;
    @Column(name = "SEQUENCIAL")
    private int sequencial;

    public LendingNumber(int year, int number) {
        this.year = year;
        this.sequencial = number;
        this.lendingNumber = year + "/" + number;
    }

    public LendingNumber(String lendingNumber){
        int a, b;
        try { //TODO: Ricardo: Should this logic be here?
            a = Integer.parseInt(lendingNumber, 0, 3, 1);
            b = Integer.parseInt(lendingNumber, 5, lendingNumber.length(), 1);
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            return;
        }
        this.year = a;
        this.sequencial = b;
        this.lendingNumber = a + "/" + b;
    }

    public LendingNumber(int number) {
        this.year = LocalDate.now().getYear();
        this.sequencial = number;
        this.lendingNumber = year + "/" + number;
    }

    public LendingNumber() {
        // for ORM only
    }

    public String toString() {
        return this.lendingNumber;
    }

}
