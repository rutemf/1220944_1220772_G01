package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;

@Entity
public class Fine {
    private final int FINE_VALUE_PER_DAY_IN_CENTS = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="FINE_ID")
    private Long fineId;

    @OneToOne
    @JoinColumn(name = "LENDING_NUMBER")
    Lending lending;

    @Basic
    int centsValue; // Fine value in euro cents

    @Basic
    boolean paid;

    protected Fine() {
        // for ORM only
    }

    public Fine(Lending lending){
        this.lending = lending;
        paid = false;

        int del = lending.getDaysDelayed();
        if(del > 0){
            this.centsValue = del * FINE_VALUE_PER_DAY_IN_CENTS;
        }
    }

}
