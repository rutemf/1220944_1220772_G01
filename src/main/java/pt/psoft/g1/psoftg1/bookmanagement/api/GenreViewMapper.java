package pt.psoft.g1.psoftg1.bookmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;


import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GenreViewMapper extends MapperInterface{
    @Mapping(target = "genre", source = "genre")
    public abstract GenreView toGenreView(Genre genre);

    public abstract GenreBookCountView toGenreBookCountView(GenreBookCountDTO genreBookCountView);

    public abstract List<GenreBookCountView> toGenreBookCountView(List<GenreBookCountDTO> genreBookCountView);

    public abstract List<GenreView> toBookView(List<Genre> genreList);

}
