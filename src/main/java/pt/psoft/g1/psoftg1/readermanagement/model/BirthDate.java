package pt.psoft.g1.psoftg1.readermanagement.model;


import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Embeddable
@NoArgsConstructor
public class BirthDate {
    LocalDateTime date;

    private final String dateFormatPatternRegex = "\\d{4}/\\d{2}/\\d{2}";

    //TODO: Colocar este valor a partir do application.properties ou de outro ficheiro de configuração
    private int minimumAge = 12;

    public BirthDate(int year, int month, int day) throws Exception {
        setDate(year, month, day);
    }

    public BirthDate(String date) throws Exception {
        if(!date.matches(dateFormatPatternRegex)) {
            throw new Exception("Provided birth date is not in a valid format");
        }

        String[] dateParts = date.split("/");

        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        setDate(year, month, day);
    }

    private void setDate(int year, int month, int day) throws Exception {
        LocalDate minimumAgeDate = LocalDate.now().minusYears(minimumAge);
        LocalDate userDate = LocalDate.of(year, month, day);
        if(userDate.isAfter(minimumAgeDate)) {
            throw new Exception("User age must be, at least, " + minimumAge);
        }

        this.date = LocalDateTime.of(userDate, LocalTime.of(0,0));
    }
}
