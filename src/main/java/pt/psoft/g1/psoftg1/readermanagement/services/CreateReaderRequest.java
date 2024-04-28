package pt.psoft.g1.psoftg1.readermanagement.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

@Data
@NoArgsConstructor
public class CreateReaderRequest {
    private int number;

    @NonNull
    private User user;

    @NonNull
    @NotBlank
    private String fullName;

    @NonNull
    @NotBlank
    @Email
    private String emailAddress;

    @NonNull
    @NotBlank
    private String birthDate;

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

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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
