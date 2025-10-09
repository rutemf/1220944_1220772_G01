package pt.psoft.g1.psoftg1.authormanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Getter
@Setter
@Document(collection = "authors")
public class AuthorNoSQL {

    //gerar id da base de dados com base65

    private Long authorNumber;
    private long version;
    private Name name;
    private Bio bio;

    public AuthorNoSQL(Author author) {
        this.authorNumber = author.getAuthorNumber();
        this.version = author.getVersion();
        this.name = author.getName();
        this.bio = author.getBio();
    }

    protected AuthorNoSQL() {
    }

    public Author toDomain() {
        return new Author(
                authorNumber,
                version,
                name != null ? new Name(name.toString()) : null,
                bio != null ? new Bio(bio.toString()) : null
        );
    }

}
