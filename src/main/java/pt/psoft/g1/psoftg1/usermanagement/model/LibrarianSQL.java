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
    }

    protected LibrarianSQL() { }

    public Librarian toDomain() {
        return null;
    }

    public static LibrarianSQL fromDomain(Librarian librarian) {
        return new LibrarianSQL(librarian);
    }
}