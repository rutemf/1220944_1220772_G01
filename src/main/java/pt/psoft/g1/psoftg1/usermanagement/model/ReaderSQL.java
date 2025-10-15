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
        this.addAuthority(new Role(Role.READER));
    }

    protected ReaderSQL() {}

    public Reader toDomain() {
        String fullName = this.getName();
        return Reader.newReader(this.getUsername(), this.getPassword(), fullName);
    }


    public static ReaderSQL fromDomain(Reader reader) {
        return new ReaderSQL(reader);
    }
}
