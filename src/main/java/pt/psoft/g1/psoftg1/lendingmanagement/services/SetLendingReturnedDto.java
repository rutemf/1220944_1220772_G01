package pt.psoft.g1.psoftg1.lendingmanagement.services;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A DTO for setting a Lending as returned")
public class SetLendingReturnedDto {
    private String readerNumber;
    private String lendingNumber;
    private String isbn;
    private String commentary;

}
