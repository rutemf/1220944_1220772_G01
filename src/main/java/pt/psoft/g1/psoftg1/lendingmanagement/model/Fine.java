package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;

@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="FINE_ID")
    private Long fineId;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "YEAR"),
            @JoinColumn(name = "NUMBER")
    })
    Lending lending;

    @Basic
    int centsValue; // Fine value in euro cents

    @Basic
    boolean paid;

    protected Fine() {
        // for ORM only
    }

}
