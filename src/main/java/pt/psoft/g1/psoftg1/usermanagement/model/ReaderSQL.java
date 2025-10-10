package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReaderSQL extends UserSQL {

    public ReaderSQL(Reader reader) {
        super(reader);
    }

    protected ReaderSQL() {}

    public Reader toDomain() {
        return null;
    }

    public static ReaderSQL fromDomain(Reader reader) {
        return new ReaderSQL(reader);
    }
}
