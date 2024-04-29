package pt.psoft.g1.psoftg1.bookmanagement.services;
import jakarta.validation.ValidationException;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;

@Mapper(componentModel = "spring")
public abstract class EditBookMapper {

    private GenreRepository genreRepository;

    public static Book create(pt.psoft.g1.psoftg1.CreateBookRequest request) {
        return null;
    }

    public abstract void update(pt.psoft.g1.psoftg1.EditBookRequest request, @MappingTarget Book book);

    public Genre toGenre(final String genre) {
       // return genreRepository.findByName(new Genre(genre)).orElseThrow(() -> new ValidationException("Select an existing genre"));
        return null;
    }
}
