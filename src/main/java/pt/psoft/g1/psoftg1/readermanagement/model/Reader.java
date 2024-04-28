package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.time.LocalDate;

@Entity
public class Reader extends User {
    @Getter
    @EmbeddedId
    private ReaderNumber readerNumber;

    @Embedded
    private Name fullName;

    @Getter
    @Embedded
    private EmailAddress emailAddress;

    @Embedded
    private BirthDate birthDate;

    @Getter
    @Embedded
    private PhoneNumber phoneNumber;

    @Basic
    private boolean gdprConsent;

    @Basic
    private boolean marketingConsent;

    @Basic
    private boolean thirdPartySharingConsent;

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

    public Reader(int readerNumber, String username, String password, String fullName, String emailAddress, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty) throws Exception {
        super(username, password);

        if(fullName == null || emailAddress == null || phoneNumber == null) {
            throw new Exception("Provided argument resolves to null object");
        }

        setReaderNumber(new ReaderNumber(LocalDate.now().getYear(), readerNumber));
        setFullName(new Name(fullName));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        setEmailAddress(new EmailAddress(emailAddress));
        setBirthDate(new BirthDate(birthDate));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        //By the client specifications, gdpr can only have the value of true. A setter will be created anyways in case we have accept no gdpr consent later on the project
        setGdprConsent(gdpr);

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

    private void setFullName(Name fullName) {
        if(fullName != null) {
            this.fullName = fullName;
        }
    }

    private void setEmailAddress(EmailAddress email) {
        if(email != null) {
            this.emailAddress = email;
        }
    }

    private void setBirthDate(BirthDate date) {
        if(date != null) {
            this.birthDate = date;
        }
    }

    private void setGdprConsent(boolean gdpr) {
        this.gdprConsent = gdpr;
    }

    private void setMarketingConsent(boolean marketing) {
        this.marketingConsent = marketing;
    }

    private void setThirdPartySharingConsent(boolean third) {
        this.thirdPartySharingConsent = third;
    }

    //TODO: Apply Patch method to update the properties we want


    protected Reader() {
        // for ORM only
    }

}
