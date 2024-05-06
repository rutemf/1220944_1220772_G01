package pt.psoft.g1.psoftg1.authormanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.shared.api.ViewMapper;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper extends ViewMapper {

    @Mapping(target = "authorNumber", source = "authorNumber")
    @Mapping(target = "name", source = "author.name")
    @Mapping(target = "bio", source = "author.bio")
    public abstract AuthorView toAuthorView(Author author);

    public abstract Iterable<AuthorView> toAuthorView(Iterable<Author> authors);




}
