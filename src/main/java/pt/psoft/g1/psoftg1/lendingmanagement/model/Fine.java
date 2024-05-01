package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * The {@code Fine} class models a fine applied when a lending is past its due date.
 * <p>It stores its current value, and if it has been paid.
 * @author  rmfranca*/
@Getter
@Embeddable
public class Fine {
    @Transient
    private static final int FINE_VALUE_PER_DAY_IN_CENTS = 300;    //TODO: Move this to a properties file

    /**Fine value in Euro cents*/
    int centsValue;

    boolean paid = false;

    /**Protected empty constructor for ORM only.*/
    protected Fine() {}

    /**
     * Constructs a new {@code Fine} object and sets the current value of the fine.
     * @param   daysDelayed number of days which have passed since the limit date on the associated {@code Lending}
     * */
    public Fine(int daysDelayed) {
        setValue(daysDelayed);
    }

    /**
     * Sets the {@code value} object and sets the current value of the fine.
     * <p>
     * If {@code paid} is true, it exits the method. Otherwise, the value is updated based on {@code daysDelayed}
     * * {@link Fine#FINE_VALUE_PER_DAY_IN_CENTS}
     * @param   daysDelayed number of days which have passed since the limit date on the associated {@code Lending}
     * */
    public void setValue(int daysDelayed) {
        if (paid){
            return;
        }
        if (daysDelayed > 0) {
            this.centsValue = daysDelayed * FINE_VALUE_PER_DAY_IN_CENTS;
        }
    }

    /**
     * Sets the fine as paid
     * */
    public void setPaid() {
        this.paid = true;
    }
}
