package readers.readers.services;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Schema(description = "Representação pública de um Leitor")
@Setter
public class ReaderView {

    private String readerId;

    private String readerNumber;

    private String fullName;

    private String email;

    private String birthDate;

    private List<String> interestList;
}