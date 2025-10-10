package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

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
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
        this.id = IDGeneratorService.generateIdSQL();
        this.authorNumber = author.getAuthorNumber();
        this.name = author.getName().toString();
        this.bio = author.getBio().toString();
    }

    // JPA
    protected AuthorSQL() {
    }

    public Author toDomain() {
        return new Author(
                authorNumber,
                new Name(name),
                new Bio(bio)
        );
    }

    public static AuthorSQL fromDomain(Author author) {
        return new AuthorSQL(author);
    }
}
