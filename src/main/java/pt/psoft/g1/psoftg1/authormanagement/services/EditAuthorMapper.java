package pt.psoft.g1.psoftg1;
import jakarta.validation.ValidationException;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EditAuthorMapper {

    @Autowired
    public abstract Author create(CreateAuthorRequest request);
    public abstract void update(EditAuthorRequest request, @MappingTarget Author author);
    public Name to Name(final String name) {
        return nameRepository.findByName(name).orElseThrow(() -> new ValidationException("Select an existing name"));
    }
}
