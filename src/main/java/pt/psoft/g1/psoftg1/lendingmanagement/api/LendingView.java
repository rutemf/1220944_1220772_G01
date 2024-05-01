package pt.psoft.g1.psoftg1.lendingmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "A Lending")
public class LendingView {
    private Long pk;

    private String lendingNumber;

    private int fineCentsValue;

    @NotNull
    private String bookTitle;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate limitDate;

    private Integer daysUntilReturn;

    private Integer daysOverdue;
}
