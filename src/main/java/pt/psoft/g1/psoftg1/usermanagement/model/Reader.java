package pt.psoft.g1.psoftg1.usermanagement.model;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Getter
@Setter
public class Reader extends User {

    public Reader(String username, String password) {
        super(username, password);
        this.addAuthority(new Role(Role.READER));
    }

    protected Reader() { }

    public static Reader newReader(final String username, final String password, final String name) {
        final var reader = new Reader(username, password);
        reader.setName(new Name(name));
        return reader;
    }
}
