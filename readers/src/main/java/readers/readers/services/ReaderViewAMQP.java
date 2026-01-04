package readers.readers.services;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "A Reader from AMQP communication")
@NoArgsConstructor
@AllArgsConstructor
public class ReaderViewAMQP {

    @NotNull
    private String readerNumber;

    @NotNull
    private String birthDate;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Boolean gdpr;

    private List<String> interestList;

    public ReaderViewAMQP(String birthDate, String phoneNumber, Boolean gdpr,  List<String> interestList) {
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gdpr = gdpr;
        this.interestList = interestList;
    }
}
