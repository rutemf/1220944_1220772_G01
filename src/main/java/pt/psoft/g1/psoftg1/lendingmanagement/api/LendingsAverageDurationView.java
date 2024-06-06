package pt.psoft.g1.psoftg1.lendingmanagement.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LendingsAverageDurationView {
    @NotNull
    private String lendingsAverageDuration;
}
