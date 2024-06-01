package pt.psoft.g1.psoftg1.readermanagement.services;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateReaderRequest {
    @NotBlank
    @Email
    @NonNull
    private String username;

    @NotBlank
    @NonNull
    private String password;

    @NotBlank
    @NonNull
    private String fullName;

    @NonNull
    @NotBlank
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private String birthDate;

    @NonNull
    @NotBlank
    private String phoneNumber;

    private boolean gdpr;

    private boolean marketing;

    private boolean thirdParty;

    @Nullable
    @Getter
    @Setter
    private List<String> stringInterestList;

    @Nullable
    @Getter
    @Setter
    private List<Genre> interestList;

    /*public CreateReaderRequest(String username, String password, String fullName, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty) {
        setUsername(username);
        setPassword(password);
        setFullName(fullName);
        setBirthDate(birthDate);
        setPhoneNumber(phoneNumber);
        setGdpr(gdpr);
        setMarketing(marketing);
        setThirdParty(thirdParty);
    }*/

    public boolean getThirdParty() {
        return thirdParty;
    }

    public boolean getMarketing() {
        return marketing;
    }

    public boolean getGdpr() {
        return gdpr;
    }
}
