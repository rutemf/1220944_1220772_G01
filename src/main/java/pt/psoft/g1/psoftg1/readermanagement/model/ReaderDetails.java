package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.time.LocalDate;

@Entity
public class ReaderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Getter
    @Setter
    @OneToOne
    private User user;

    @Getter
    private ReaderNumber readerNumber;

    @Embedded
    @Getter
    private BirthDate birthDate;

    @Embedded
    @Getter
    private PhoneNumber phoneNumber;

    @Setter
    @Basic
    private boolean gdprConsent;

    @Setter
    @Basic
    @Getter
    private boolean marketingConsent;

    @Setter
    @Basic
    @Getter
    private boolean thirdPartySharingConsent;

/*
    /**
     * factory method. since mapstruct does not handle protected/private setters
     * neither more than one public constructor, we use these factory methods for
     * helper creation scenarios
     *
     * @param username
     * @param password
     * @param fullName
     * @return
     */

    /*public Reader newReader(final String username, final String password, final String fullName) {
        final var u = new Reader(username, password);
        u.setName(new Name(fullName));
        u.addAuthority(new Role(Role.READER));
        return u;
    }*/

    public ReaderDetails(int readerNumber, User user, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty) throws Exception {
        if(user == null || phoneNumber == null) {
            throw new IllegalArgumentException("Provided argument resolves to null object");
        }

        if(!gdpr) {
            throw new IllegalArgumentException("Readers must agree with the GDPR rules");
        }

        setUser(user);
        setReaderNumber(new ReaderNumber(LocalDate.now().getYear(), readerNumber));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        setBirthDate(new BirthDate(birthDate));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        //By the client specifications, gdpr can only have the value of true. A setter will be created anyways in case we have accept no gdpr consent later on the project
        setGdprConsent(true);

        setMarketingConsent(marketing);
        setThirdPartySharingConsent(thirdParty);
    }

    private void setPhoneNumber(PhoneNumber number) {
        if(number != null) {
            this.phoneNumber = number;
        }
    }

    private void setReaderNumber(ReaderNumber readerNumber) {
        if(readerNumber != null) {
            this.readerNumber = readerNumber;
        }
    }

    private void setBirthDate(BirthDate date) {
        if(date != null) {
            this.birthDate = date;
        }
    }

    //TODO: Edu: Apply Patch method to update the properties we want
    public void applyPatch(UpdateReaderRequest request) throws Exception {
        String birthDate = request.getBirthDate();
        String phoneNumber = request.getPhoneNumber();
        String fullName = request.getFullName();
        boolean marketing = request.getMarketing();
        boolean thirdParty = request.getThirdParty();
        String username = request.getUsername();
        String password = request.getPassword();

        if(username != null) {
            this.user.setUsername(username);
        }

        if(password != null) {
            this.user.setPassword(password);
        }

        if(birthDate != null) {
            setBirthDate(new BirthDate(birthDate));
        }

        if(phoneNumber != null) {
            setPhoneNumber(new PhoneNumber(phoneNumber));
        }

        if(marketing != this.marketingConsent) {
            setMarketingConsent(marketing);
        }

        if(thirdParty != this.thirdPartySharingConsent) {
            setThirdPartySharingConsent(thirdParty);
        }
    }

    protected ReaderDetails() {
        // for ORM only
    }
}
