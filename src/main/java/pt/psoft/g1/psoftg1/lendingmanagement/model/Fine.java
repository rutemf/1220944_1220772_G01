package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

/**
 * The {@code Fine} class models a fine applied when a lending is past its due date.
 * <p>It stores its current value, and if it has been paid.
 * @author  rmfranca*/
@Getter
@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @PositiveOrZero
    @Column(updatable = false)
    private int fineValuePerDayInCents;

    /**Fine value in Euro cents*/
    @PositiveOrZero
    int centsValue;

    @OneToOne
    private Lending lending;


    /**
     * Constructs a new {@code Fine} object and sets the current value of the fine,
     * as well as the fine value per day at the time of creation.
     * @param   daysDelayed number of days which have passed since the limit date on the associated {@code Lending}
     * */
    public Fine(int daysDelayed) {
        if(daysDelayed <= 0)
            return;
        fineValuePerDayInCents = Lending.FINE_VALUE_PER_DAY_IN_CENTS;
        centsValue = fineValuePerDayInCents * daysDelayed;
    }

    /**Protected empty constructor for ORM only.*/
    protected Fine() {}
}
