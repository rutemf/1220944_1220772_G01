package pt.psoft.g1.psoftg1.authormanagement.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper extends MapperInterface {
    @Mapping(target = "photo", source = "photoURI")
    public abstract Author create(CreateAuthorRequest request);

    public abstract void update(UpdateAuthorRequest request, @MappingTarget Author author);

    protected Name map(String value) {
        return value != null ? new Name(value) : null;
    }

    protected String map(Name name) {
        return name != null ? name.toString() : null;
    }

    protected Bio mapBio(String value) {
        return value != null ? new Bio(value) : null;
    }

    protected String map(Bio bio) {
        return bio != null ? bio.toString() : null;
    }
}