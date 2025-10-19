package pt.psoft.g1.psoftg1.lendingmanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Fine implements Serializable {

    private int fineValuePerDayInCents;
    private int centsValue;
    private Lending lending;

    public Fine(Lending lending) {
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.centsValue = fineValuePerDayInCents * lending.getDaysDelayed();
        this.lending =  Objects.requireNonNull(lending);
    }

    @Override
    public String toString() {
        return "Fine: " + centsValue + " cents for lending " + lending.getLendingNumber();
    }
}
