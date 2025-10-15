package pt.psoft.g1.psoftg1.readermanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Embeddable
@Getter
@Setter
public class BirthDate {

    @Column(nullable = false, updatable = false)
    private LocalDate birthDate;
    private static final int DEFAULT_MINIMUM_AGE = 0;

    public BirthDate(LocalDate birthDate) {
        setBirthDate(birthDate);
    }

    public BirthDate(int year, int month, int day, int minimumAge) {
        setBirthDate(LocalDate.of(year, month, day));
    }

    public BirthDate(String birthDate) {
        if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Provided birth date is not in a valid format. Use yyyy-MM-dd");
        }

        LocalDate date = LocalDate.parse(birthDate);
        setBirthDate(date);
    }

    protected BirthDate() { }

    private void setBirthDate(LocalDate date) {
        if (DEFAULT_MINIMUM_AGE > 0) {
            LocalDate minimumAgeDate = LocalDate.now().minusYears(DEFAULT_MINIMUM_AGE);
            if (date.isAfter(minimumAgeDate)) {
                throw new IllegalArgumentException("User must be at least " + DEFAULT_MINIMUM_AGE + " years old");
            }
        }
        this.birthDate = date;
    }

    @Override
    public String toString() {
        return birthDate.toString();
    }
}
