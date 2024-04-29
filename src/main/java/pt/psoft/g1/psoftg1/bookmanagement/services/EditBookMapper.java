package pt.psoft.g1.psoftg1.bookmanagement.services;
import jakarta.validation.ValidationException;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;

@Mapper(componentModel = "spring")
public abstract class EditBookMapper {

    private GenreRepository genreRepository;

    public static Book create(CreateBookRequest request) {
        return null;
    }

    public abstract void update(pt.psoft.g1.psoftg1.EditBookRequest request, @MappingTarget Book book);

    public Genre toGenre(final String genre) {
       // return genreRepository.findByName(new Genre(genre)).orElseThrow(() -> new ValidationException("Select an existing genre"));
        return null;
    }

    public String map(Title title) {
        return title.toString();
    }
}
