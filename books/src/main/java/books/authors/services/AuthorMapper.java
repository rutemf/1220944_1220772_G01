package books.authors.services;

import books.authors.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import books.shared.api.MapperInterface;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper extends MapperInterface {
    @Mapping(target = "photo", source = "photoURI")
    public abstract Author create(CreateAuthorRequest request);

    public abstract void update(UpdateAuthorRequest request, @MappingTarget Author author);

}
