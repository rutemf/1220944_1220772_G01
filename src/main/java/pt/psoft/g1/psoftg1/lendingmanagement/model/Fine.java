package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fine {

    @Id
    private String id;
    private int fineValuePerDayInCents;
    private int centsValue;
    private Lending lending;

    public Fine(Lending lending) {
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.centsValue = fineValuePerDayInCents;
        this.lending = lending;
    }

    @Override
    public String toString() {
        return "Fine: " + centsValue + " cents for lending " + lending.getLendingNumber();
    }
}
