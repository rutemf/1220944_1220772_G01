package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Entity
@Getter
@Setter
public class ReaderSQL extends UserSQL {

    @Id
    private String id;

    public ReaderSQL(Reader reader) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdSQL();
    }

    protected ReaderSQL() {}

    public Reader toDomain() {
        return null;
    }

    public static ReaderSQL fromDomain(Reader reader) {
        return new ReaderSQL(reader);
    }
}
