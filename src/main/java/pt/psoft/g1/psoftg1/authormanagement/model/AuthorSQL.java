package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Entity
@Getter
@Setter
@Table(name = "Author")
public class AuthorSQL {

    @Id
    private String id;

    private Long authorNumber;

    @Embedded
    private Name name;

    @Embedded
    private Bio bio;

    public AuthorSQL(Author author) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdSQL();
        this.authorNumber = author.getAuthorNumber();
        this.name = author.getName();
        this.bio = author.getBio();
    }

    // JPA
    protected AuthorSQL() {
    }

    public Author toDomain() {
        return new Author(
                authorNumber,
                name,
                bio != null ? bio : null
        );
    }

    public static AuthorSQL fromDomain(Author author) {
        return new AuthorSQL(author);
    }
}
