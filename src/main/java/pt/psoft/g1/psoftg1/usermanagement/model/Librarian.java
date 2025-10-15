package pt.psoft.g1.psoftg1.usermanagement.model;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Getter
@Setter
public class Librarian extends User {

    public Librarian(String username, String password) {
        super(username, password);
        this.addAuthority(new Role(Role.LIBRARIAN));
    }

    protected Librarian() { }

    public static Librarian newLibrarian(final String username, final String password, final String name) {
        final var u = new Librarian(username, password);
        u.setName(new Name(name));
        return u;
    }
}
