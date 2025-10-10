package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Entity
@Getter
@Setter
public class LibrarianSQL extends User {

    @Id
    private String id;

    public LibrarianSQL(Librarian librarian) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdSQL();
    }

    protected LibrarianSQL() { }

    public Librarian toDomain() {
        return null;
    }

    public static LibrarianSQL fromDomain(Librarian librarian) {
        return new LibrarianSQL(librarian);
    }
}