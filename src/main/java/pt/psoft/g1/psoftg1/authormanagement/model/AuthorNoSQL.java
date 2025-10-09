package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Getter
@Setter
@Document(collection = "authors")
public class AuthorNoSQL {

    @Id
    private String id;

    private Long authorNumber;
    private long version;
    private Name name;
    private Bio bio;

    public AuthorNoSQL(Author author) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdNoSQL();
        this.authorNumber = author.getAuthorNumber();
        this.name = author.getName();
        this.bio = author.getBio();
    }

    // NoSQL
    protected AuthorNoSQL() {
    }

    public Author toDomain() {
        return new Author(
                authorNumber,
                name != null ? new Name(name.toString()) : null,
                bio != null ? new Bio(bio.toString()) : null
        );
    }

    public static AuthorNoSQL fromDomain(Author author) {
        return new AuthorNoSQL(author);
    }

}
