package readers.readers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import readers.genres.model.Genre;

import java.util.List;

@Entity
@Getter
@Setter
public class ReaderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String readerId;

    @Embedded
    private ReaderNumber readerNumber;

    @Embedded
    private BirthDate birthDate;

    @Embedded
    private PhoneNumber phoneNumber;

    private boolean gdprConsent;

    private boolean marketingConsent;

    private boolean thirdPartySharingConsent;

    @Enumerated(EnumType.STRING)
    private ReaderStatus status;

    @OneToMany
    private List<Genre> interestList;

    public ReaderDetails(int readerNumber, String readerId, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, List<Genre> interestList) {
        if (readerId == null || phoneNumber == null) {
            throw new IllegalArgumentException("Provided argument resolves to null object");
        }

        if (!gdpr) {
            throw new IllegalArgumentException("Readers must agree with the GDPR rules");
        }

        this.readerId = readerId;
        this.status = ReaderStatus.PENDING;
        setReaderNumber(new ReaderNumber(readerNumber));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        setBirthDate(new BirthDate(birthDate));
        setGdprConsent(true);

        setMarketingConsent(marketing);
        setThirdPartySharingConsent(thirdParty);
        setInterestList(interestList);
    }

    protected ReaderDetails() { }

    private void setPhoneNumber(PhoneNumber number) {
        if (number != null) {
            this.phoneNumber = number;
        }
    }

    private void setReaderNumber(ReaderNumber readerNumber) {
        if (readerNumber != null) {
            this.readerNumber = readerNumber;
        }
    }

    private void setBirthDate(BirthDate date) {
        if (date != null) {
            this.birthDate = date;
        }
    }
}
