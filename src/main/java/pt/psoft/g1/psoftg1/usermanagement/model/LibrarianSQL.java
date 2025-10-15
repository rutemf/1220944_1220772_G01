package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LibrarianSQL extends UserSQL {

    public LibrarianSQL(Librarian librarian) {
        super(librarian);
        this.addAuthority(new Role(Role.LIBRARIAN));
    }

    protected LibrarianSQL() { }

    public Librarian toDomain() {
        String fullName = this.getName();
        return Librarian.newLibrarian(this.getUsername(), this.getPassword(), fullName);
    }

    public static LibrarianSQL fromDomain(Librarian librarian) {
        return new LibrarianSQL(librarian);
    }
}