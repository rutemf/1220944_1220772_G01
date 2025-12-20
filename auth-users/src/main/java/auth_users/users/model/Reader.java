package auth_users.users.model;

import jakarta.persistence.Entity;

@Entity
public class Reader extends User {

    public Reader(String username, String password) {
        super(username, password);
        this.addAuthority(new Role(Role.READER));
    }

    protected Reader() { }

    public static Reader newReader(final String username, final String password, final String name) {
        final var u = new Reader(username, password);
        u.setName(name);
        return u;
    }
}