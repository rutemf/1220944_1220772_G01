package pt.psoft.g1.psoftg1.readermanagement.model;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.nio.file.InvalidPathException;
import java.util.List;

@Getter
@Setter
public class ReaderDetails extends EntityWithPhoto {

    private Reader reader;
    private ReaderNumber readerNumber;
    private BirthDate birthDate;
    private PhoneNumber phoneNumber;
    private boolean gdprConsent;
    private boolean marketingConsent;
    private boolean thirdPartySharingConsent;
    private List<Genre> interestList;

    public ReaderDetails(int readerNumber, Reader reader, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, String photoURI, List<Genre> interestList) {
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

        setPhotoInternal(photoURI);
        setMarketingConsent(marketing);
        setThirdPartySharingConsent(thirdParty);
        setInterestList(interestList);
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

    public void applyPatch(final long currentVersion, final UpdateReaderRequest request, String photoURI, List<Genre> interestList) {

        String birthDate = request.getBirthDate();
        String phoneNumber = request.getPhoneNumber();
        boolean marketing = request.getMarketing();
        boolean thirdParty = request.getThirdParty();
        String fullName = request.getFullName();
        String username = request.getUsername();
        String password = request.getPassword();

        if(username != null) {
            this.reader.setUsername(username);
        }

        if(password != null) {
            this.reader.setPassword(password);
        }

        if(fullName != null) {
            this.reader.setName(new Name(fullName));
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
                setPhotoInternal(photoURI);
            } catch(InvalidPathException ignored) {}
        }

        if(interestList != null) {
            this.interestList = interestList;
        }
    }

    public void removePhoto(long desiredVersion) {

        setPhotoInternal(null);
    }

    public String getReaderNumber(){
        return this.readerNumber.toString();
    }

    public String getPhoneNumber() { return this.phoneNumber.toString();}

    protected ReaderDetails() {
        // for ORM only
    }
}
