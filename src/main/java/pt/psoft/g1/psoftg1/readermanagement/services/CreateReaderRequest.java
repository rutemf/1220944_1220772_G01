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
    private int number;

    @Getter
    @NonNull
    private User user;

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

    @NonNull
    private boolean gdpr;

    @NonNull
    private boolean marketing;

    @NonNull
    private boolean thirdParty;

    public CreateReaderRequest(User user, String fullName, String emailAddress, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty) {
        this.user = user;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gdpr = gdpr;
        this.marketing = marketing;
        this.thirdParty = thirdParty;
    }

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
