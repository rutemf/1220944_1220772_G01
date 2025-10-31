package pt.psoft.g1.psoftg1.usermanagement.dataschema;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

@Getter
@Setter
public class LibrarianNoSQL extends UserNoSQL {

    public LibrarianNoSQL(Librarian librarian) {
        super(librarian);
        this.addAuthority(new Role(Role.LIBRARIAN));
    }

    protected LibrarianNoSQL() { }

    @Override
    public Librarian toDomain() {
        Librarian librarian = Librarian.newLibrarian(this.getUsername(), this.getPassword(), String.valueOf(this.getName()));
        librarian.getAuthorities().addAll(this.getAuthorities());
        return librarian;
    }

    public static LibrarianNoSQL fromDomain(Librarian librarian) {
        return new LibrarianNoSQL(librarian);
    }

}
