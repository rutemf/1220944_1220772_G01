package authors.authors.repositories;

import authors.authors.model.Author;
import authors.authors.services.SearchAuthorsQuery;
import authors.shared.services.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository{

    Optional<Author> findByAuthorNumber(@Param("authorNumber")Long id);

    Author save (Author author);

    void delete (Author author);

    Optional<Author>  findByAuthorName(@Param("name") String name);

    List<Author> searchAuthors(Page page, SearchAuthorsQuery query);


}
