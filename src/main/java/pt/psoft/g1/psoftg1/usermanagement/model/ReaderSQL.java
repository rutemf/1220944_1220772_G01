package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Entity
@Getter
@Setter
public class ReaderSQL extends UserSQL {

    @Id
    private String id;

    public ReaderSQL(Reader reader) {
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
        this.id = IDGeneratorService.generateIdSQL();
    }

    protected ReaderSQL() {}

    public Reader toDomain() {
        return null;
    }

    public static ReaderSQL fromDomain(Reader reader) {
        return new ReaderSQL(reader);
    }
}
