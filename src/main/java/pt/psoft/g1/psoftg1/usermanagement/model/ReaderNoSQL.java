package pt.psoft.g1.psoftg1.usermanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Getter
@Setter
@Document(collection = "readers")
public class ReaderNoSQL extends UserNoSQL {

    protected ReaderNoSQL() {
    }
    public ReaderNoSQL(Reader reader) {
        super(reader);
        this.addAuthority(new Role(Role.READER));
    }
    @Override
    public Reader toDomain() {
        Reader reader = Reader.newReader(this.getUsername(), this.getPassword(), this.getName());
        reader.getAuthorities().addAll(this.getAuthorities());
        return reader;
    }
    public static ReaderNoSQL fromDomain(Reader reader) {
        return new ReaderNoSQL(reader);
    }

}
