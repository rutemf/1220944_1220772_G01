package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;

@Embeddable
public class Fine {
    private final int FINE_VALUE_PER_DAY_IN_CENTS = 300;

    @Basic
    int centsValue; // Fine value in euro cents

    @Basic
    boolean paid;

    protected Fine() {
        // for ORM only
    }

    public Fine(int daysDelayed){
        paid = false;
        if(daysDelayed > 0){
            this.centsValue = daysDelayed * FINE_VALUE_PER_DAY_IN_CENTS;
        }
    }
}
