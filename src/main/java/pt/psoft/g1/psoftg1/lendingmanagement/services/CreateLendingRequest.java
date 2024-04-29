package pt.psoft.g1.psoftg1.lendingmanagement.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A DTO for editing or creating a Lending")
public class CreateLendingRequest {

    @Size(min = 8, max = 16)
    private String isbn;

    @Size(min = 4, max = 16)
    private String readerNumber;
}
