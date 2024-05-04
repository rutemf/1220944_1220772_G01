package pt.psoft.g1.psoftg1.readermanagement.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

@Data
@NoArgsConstructor
public class CreateReaderRequest {
    @Setter
    @Getter
    private String number;

    @Getter
    @NonNull
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @NonNull
    @NotBlank
    private String fullName;

    @Getter
    @NonNull
    @NotBlank
    @Email
    private String emailAddress;

    @Getter
    @NonNull
    @NotBlank
    private String birthDate;

    @Getter
    @NonNull
    @NotBlank
    private String phoneNumber;

    private boolean gdpr;

    private boolean marketing;

    private boolean thirdParty;

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
