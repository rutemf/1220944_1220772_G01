package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Entity
@Getter
@Setter
public class LibrarianSQL extends User {

    @Id
    private String id;

    public LibrarianSQL(Librarian librarian) {
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
        this.id = IDGeneratorService.generateIdSQL();
    }

    protected LibrarianSQL() { }

    public Librarian toDomain() {
        return null;
    }

    public static LibrarianSQL fromDomain(Librarian librarian) {
        return new LibrarianSQL(librarian);
    }
}