package pt.psoft.g1.psoftg1.readermanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;


@Embeddable
@Getter
@Setter
public class BirthDate {

    @Column(nullable = false, updatable = false)
    LocalDate birthDate;

    public BirthDate(LocalDate birthDate, int minimumAge) {
        setBirthDate(birthDate, minimumAge);
    }

    public BirthDate(int year, int month, int day, int minimumAge) {
        setBirthDate(LocalDate.of(year, month, day), minimumAge);
    }

    public BirthDate(String birthDate, int minimumAge) {
        if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Provided birth date is not in a valid format. Use yyyy-MM-dd");
        }

        LocalDate date = LocalDate.parse(birthDate);
        setBirthDate(date, minimumAge);
    }

    protected BirthDate() { }

    private void setBirthDate(LocalDate date, int minimumAge) {
        if (minimumAge > 0) {
            LocalDate minimumAgeDate = LocalDate.now().minusYears(minimumAge);
            if (date.isAfter(minimumAgeDate)) {
                throw new IllegalArgumentException("User must be at least " + minimumAge + " years old");
            }
        }
        this.birthDate = date;
    }

    @Override
    public String toString() {
        return birthDate.toString();
    }
}
