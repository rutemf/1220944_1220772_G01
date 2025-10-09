package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Entity
@Getter
@Setter
@Table(name = "Author")
public class AuthorSQL {

    //gerar id da base de dados com base65

    @Id
    private Long authorNumber;

    private long version;

    private Name name;

    private Bio bio;

    public AuthorSQL(Author author) {
        this.authorNumber = author.getAuthorNumber();
        this.version = author.getVersion();
        this.name = author.getName();
        this.bio = author.getBio();
    }

    public AuthorSQL() {

    }
}
