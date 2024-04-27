package pt.psoft.g1.psoftg1;
import jakarta.validation.ValidationException;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EditBookMapper {

    @Autowired
    private GenreRepository genreRepository;

    public abstract Book create(CreateBookRequest request);

    public abstract void update(EditBookRequest request, @MappingTarget Book book);

    public Genre toGenre(final String genre) {
        return genreRepository.findByName(genre).orElseThrow(() -> new ValidationException("Select an existing genre"));
    }
}
