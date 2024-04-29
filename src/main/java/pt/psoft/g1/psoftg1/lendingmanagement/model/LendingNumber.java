package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LendingNumber implements Serializable {
    @Column(name = "LENDING_NUMBER")
    private String lendingNumber;

    public LendingNumber(int year, int number) {
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
        this.lendingNumber = a + "/" + b;
    }

    public LendingNumber() {
        // for ORM only
    }
}
