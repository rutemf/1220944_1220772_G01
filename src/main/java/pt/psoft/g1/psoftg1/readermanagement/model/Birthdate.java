package pt.psoft.g1.psoftg1.readermanagement.model;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Past;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Embeddable
@NoArgsConstructor
public class Birthdate {
    LocalDateTime date;

    Birthdate(int year, int month, int day){
        this.date = LocalDateTime.of(
                LocalDate.of(year, month, day),
                LocalTime.of(0, 0,0));
    }
}
