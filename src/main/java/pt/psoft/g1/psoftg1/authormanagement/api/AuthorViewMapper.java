package pt.psoft.g1.psoftg1.authormanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper extends MapperInterface {

    @Mapping(target = "authorNumber", source = "authorNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "bio", source = "bio")
    public abstract AuthorView toAuthorView(Author author);

    public abstract List<AuthorView> toAuthorView(List<Author> authors);




}
