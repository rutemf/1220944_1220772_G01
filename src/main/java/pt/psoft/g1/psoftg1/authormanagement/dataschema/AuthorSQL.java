package pt.psoft.g1.psoftg1.authormanagement.dataschema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.util.concurrent.ThreadLocalRandom;

@Entity
@Getter
@Setter
@Table(name = "Author")
public class AuthorSQL {

    @Id
    private String id;

    private Long authorNumber;

    private String name;

    private String bio;

    public AuthorSQL(Author author) {
        this.id = IDGeneratorService.generateIdSQL();
        this.authorNumber = (author.getAuthorNumber() != null) ? author.getAuthorNumber() : ThreadLocalRandom.current().nextLong(10L, 1001L);
        this.name = author.getName().toString();
        this.bio = author.getBio().toString();
    }

    // JPA
    protected AuthorSQL() { }

    public Author toDomain() {
        return new Author(authorNumber, new Name(name), new Bio(bio));
    }

    public static AuthorSQL fromDomain(Author author) {
        return new AuthorSQL(author);
    }
}