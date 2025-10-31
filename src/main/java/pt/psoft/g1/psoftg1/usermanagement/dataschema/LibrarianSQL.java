package pt.psoft.g1.psoftg1.usermanagement.dataschema;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

@Entity
@Getter
@Setter
@DiscriminatorValue("LIBRARIAN")
public class LibrarianSQL extends UserSQL {

    public LibrarianSQL(Librarian librarian) {
        super(librarian);
        this.addAuthority(new Role(Role.LIBRARIAN));
    }

    protected LibrarianSQL() { }

    @Override
    public Librarian toDomain() {
        Librarian librarian = Librarian.newLibrarian(this.getUsername(), this.getPassword(), this.getName());
        librarian.getAuthorities().addAll(this.getAuthorities());
        return librarian;
    }

    public static LibrarianSQL fromDomain(Librarian librarian) {
        return new LibrarianSQL(librarian);
    }
}