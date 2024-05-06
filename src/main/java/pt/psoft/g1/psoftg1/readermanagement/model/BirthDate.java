package pt.psoft.g1.psoftg1.readermanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Embeddable
@NoArgsConstructor
public class BirthDate {
    @Getter
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    LocalDate date;

    @Transient
    private final String dateFormatPatternRegex = "\\d{4}/\\d{2}/\\d{2}";

    @Transient
    //TODO: Colocar este valor a partir do application.properties ou de outro ficheiro de configuração
    private int minimumAge = 12; //TODO: Ricardo : colocar static/final?

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

        this.date = userDate;
    }

    public String toString() {
        return String.format("%d/%d/%d", this.date.getYear(), this.date.getMonthValue(), this.date.getDayOfMonth());
    }
}
