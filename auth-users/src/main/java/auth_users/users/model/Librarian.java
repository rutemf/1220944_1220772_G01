package auth_users.users.model;

import jakarta.persistence.Entity;

@Entity
public class Librarian extends User {

    public Librarian(String username, String password) {
        super(username, password);
    }

    protected Librarian() { }

    public static Librarian newLibrarian(final String username, final String password, final String name) {
        final var u = new Librarian(username, password);
        u.setName(name);
        u.addAuthority(new Role(Role.LIBRARIAN));
        return u;
    }
}
