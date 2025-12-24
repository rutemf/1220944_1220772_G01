package readers.readers.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateReaderRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;

    @NotBlank
    private String birthDate;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Boolean gdpr;

    private List<String> interestList;

    private boolean marketing;

    private boolean thirdParty;
}
