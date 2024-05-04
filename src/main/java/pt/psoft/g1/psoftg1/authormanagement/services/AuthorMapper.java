package pt.psoft.g1.psoftg1.authormanagement.services;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.shared.api.ViewMapper;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper extends ViewMapper {
    private AuthorRepository authorRepository;
    public abstract Author create(CreateAuthorRequest request);
    public abstract void update(UpdateAuthorRequest request, @MappingTarget Author author);
    public Author findByName(final String name) {
        //return authorRepository.findByName(new Name(name)).orElseThrow(() -> new ValidationException("Select an existing name"));
        return null;
    }
}
