package pt.psoft.g1.psoftg1.usermanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("READER")
public class ReaderSQL extends UserSQL {

    public ReaderSQL(Reader reader) {
        super(reader);
        this.addAuthority(new Role(Role.READER));
    }

    protected ReaderSQL() {}

    @Override
    public Reader toDomain() {
        Reader reader = Reader.newReader(this.getUsername(), this.getPassword(), this.getName());
        reader.getAuthorities().addAll(this.getAuthorities());
        return reader;
    }

    public static ReaderSQL fromDomain(Reader reader) {
        return new ReaderSQL(reader);
    }
}
