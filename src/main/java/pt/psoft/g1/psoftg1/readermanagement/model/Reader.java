package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.time.LocalDate;

@Entity
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Getter
    @OneToOne
    private ReaderDetails readerDetails;

    @Getter
    @Setter
    @OneToOne
    private User user;

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

    public Reader(int readerNumber, User user, String fullName, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty) throws Exception {
        if(fullName == null || phoneNumber == null) {
            throw new Exception("Provided argument resolves to null object");
        }

        if(!gdpr) {
            throw new Exception("Readers must agree with the GDPR rules");
        }

        setUser(user);

        this.readerDetails = new ReaderDetails(readerNumber, fullName, birthDate, phoneNumber, gdpr, marketing, thirdParty);
    }

    public void applyPatch(UpdateReaderRequest request) throws Exception {
        this.readerDetails.applyPatch(request);

        String username = request.getUsername();
        String password = request.getPassword();

        if(username != null) {
            this.user.setUsername(username);
        }

        if(password != null) {
            this.user.setPassword(password);
        }
    }

    protected Reader() {
        // for ORM only
    }

}
