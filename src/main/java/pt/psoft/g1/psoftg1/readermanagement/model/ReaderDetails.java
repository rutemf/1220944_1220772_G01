package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Entity
@Table(name = "READER_DETAILS")
public class ReaderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Getter
    @Setter
    @OneToOne
    private Reader reader;

    @Getter
    private ReaderNumber readerNumber;

    @Embedded
    @Getter
    private BirthDate birthDate;

    @Embedded
    @Getter
    private PhoneNumber phoneNumber;

    @Setter
    @Getter
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

    @Getter
    @Setter
    @OneToOne
    private Photo photo;

    @Version
    @Getter
    private Long version;

    @Getter
    @Setter
    //TODO: Fica many to many ou one to many?
    @OneToMany
    private List<Genre> interestList;

    public ReaderDetails(int readerNumber, Reader reader, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, String photoURI) {
        if(reader == null || phoneNumber == null) {
            throw new IllegalArgumentException("Provided argument resolves to null object");
        }

        if(!gdpr) {
            throw new IllegalArgumentException("Readers must agree with the GDPR rules");
        }

        setReader(reader);
        setReaderNumber(new ReaderNumber(readerNumber));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        setBirthDate(new BirthDate(birthDate));
        //By the client specifications, gdpr can only have the value of true. A setter will be created anyways in case we have accept no gdpr consent later on the project
        setGdprConsent(true);

        if(photoURI != null) {
            try {
                //If the Path object instantiation succeeds, it means that we have a valid Path
                this.photo = new Photo(Paths.get(photoURI));
            } catch (InvalidPathException e) {
                //For some reason it failed, let's set to null to avoid invalid references to photos
                this.photo = null;
            }
        } else {
            this.photo = null;
        }

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

    private void setPhoto(Photo photo) {
        this.photo = photo;
    }

    //TODO: Edu: Apply Patch method to update the properties we want
    public void applyPatch(final long currentVersion, final UpdateReaderRequest request) {
        if(currentVersion != this.version) {
            throw new ConflictException("Provided version does not match latest version of this object");
        }
        String birthDate = request.getBirthDate();
        String phoneNumber = request.getPhoneNumber();
        boolean marketing = request.getMarketing();
        boolean thirdParty = request.getThirdParty();
        String username = request.getUsername();
        String password = request.getPassword();
        String photoURI = request.getPhotoURI();

        if(username != null) {
            this.reader.setUsername(username);
        }

        if(password != null) {
            this.reader.setPassword(password);
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

        if(photoURI != null) {
            try {
                setPhoto(new Photo(Paths.get(photoURI)));
            } catch(InvalidPathException ignored) {}
        } else {
            setPhoto(null);
        }
    }

    protected ReaderDetails() {
        // for ORM only
    }
}
