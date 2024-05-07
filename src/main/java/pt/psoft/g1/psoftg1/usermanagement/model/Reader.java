package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Entity
public class Reader extends User {
    protected Reader() {
        // for ORM only
    }
    public Reader(String username, String password) {
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

    public static Reader newReader(final String username, final String password, final String fullName) {
        final var u = new Reader(username, password);
        u.setName(new Name(fullName));
        u.addAuthority(new Role(Role.READER));
        return u;
    }
}
