package authors.authors.api;

import authors.authors.model.Author;
import authors.shared.api.MapperInterface;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AuthorsViewAMQPMapper extends MapperInterface {

    @Mapping(target = "authorNumber", source = "authorNumber")
    @Mapping(target = "name", expression = "java(author.getName().toString())")
    @Mapping(target = "bio", expression = "java(author.getBio() != null ? author.getBio().toString() : null)")
    @Mapping(target = "version", source = "version")
    public abstract AuthorsViewAMQP toAuthorsViewAMQP(Author author);

    public abstract List<AuthorsViewAMQP> toAuthorsViewAMQP(List<Author> authors);
}
