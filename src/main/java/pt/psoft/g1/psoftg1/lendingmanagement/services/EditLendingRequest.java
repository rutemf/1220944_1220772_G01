package pt.psoft.g1.psoftg1.lendingmanagement.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditLendingRequest {
    private String lendingNumber;
    private String isbn;
    private String commentary;

}
