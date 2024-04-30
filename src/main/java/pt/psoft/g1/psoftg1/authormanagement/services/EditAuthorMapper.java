package pt.psoft.g1.psoftg1.authormanagement.services;
import jakarta.validation.ValidationException;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

@Mapper(componentModel = "spring")
public abstract class EditAuthorMapper {
    private AuthorRepository authorRepository;
    public abstract Author create(CreateAuthorRequest request);
    public abstract void update(UpdateAuthorRequest request, @MappingTarget Author author);
    public Author findByName(final String name) {
        //return authorRepository.findByName(new Name(name)).orElseThrow(() -> new ValidationException("Select an existing name"));
        return null;
    }
}
