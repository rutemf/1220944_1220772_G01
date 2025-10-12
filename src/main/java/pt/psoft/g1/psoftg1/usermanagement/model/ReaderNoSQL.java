package pt.psoft.g1.psoftg1.usermanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "readers")
public class ReaderNoSQL extends UserNoSQL {
    protected ReaderNoSQL() {
    }
    public ReaderNoSQL(Reader reader) {
        super(reader);
    }
    @Override
    public Reader toDomain() {
        Reader reader = new Reader();
        reader.setUsername(getUsername());
        reader.setPassword(getPassword());
        reader.setName(getName());
        reader.getAuthorities().addAll(getAuthorities());
        reader.setEnabled(isEnabled());
        return reader;
    }
    public static ReaderNoSQL fromDomain(Reader reader) {
        return new ReaderNoSQL(reader);
    }

}
