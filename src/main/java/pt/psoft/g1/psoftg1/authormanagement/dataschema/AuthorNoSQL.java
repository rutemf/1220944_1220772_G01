package pt.psoft.g1.psoftg1.authormanagement.dataschema;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Document(collection = "authors")
public class AuthorNoSQL {

    @Id
    private String id;
    private Long authorNumber;
    private String name;
    private String bio;

    public AuthorNoSQL(Author author) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.authorNumber = (author.getAuthorNumber() != null) ? author.getAuthorNumber() : ThreadLocalRandom.current().nextLong(10L, 1001L);
        this.name = author.getName().toString();
        this.bio = author.getBio().toString();
    }

    // NoSQL
    protected AuthorNoSQL() { }

    public Author toDomain() {
        return new Author(authorNumber, new Name(name), new Bio(bio));
    }

    public static AuthorNoSQL fromDomain(Author author) {
        return new AuthorNoSQL(author);
    }
}