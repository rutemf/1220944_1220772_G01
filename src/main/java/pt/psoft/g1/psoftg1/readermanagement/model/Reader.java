package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

@Entity
public class Reader extends User {
    @EmbeddedId
    ReaderNumber readerNumber;

    @Embedded
    EmailAddress emailAddress;

    @Embedded
    Birthdate birthdate;

    @Embedded
    PhoneNumber phoneNumber;

    @Basic
    boolean gdprConsent;

    protected Reader(String username, String password){
        super(username, password);
    }

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

    public Reader newReader(final String username, final String password, final String fullName) {
        final var u = new Reader(username, password);
        u.setName(new Name(fullName));
        u.addAuthority(new Role(Role.READER));
        return u;
    }

    protected Reader() {
        // for ORM only
    }

}
