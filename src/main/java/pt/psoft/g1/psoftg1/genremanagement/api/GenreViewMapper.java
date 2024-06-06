package pt.psoft.g1.psoftg1.genremanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.util.Pair;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GenreViewMapper extends MapperInterface{

    @Mapping(target = "genre", source = "genre")
    public abstract GenreView toGenreView(Genre genre);

    public abstract GenreView mapStringToGenreView(String genre);

    @Mapping(target = "genreView", source = "genre")
    public abstract GenreBookCountView toGenreBookCountView(GenreBookCountDTO genreBookCountView);

    public abstract List<GenreBookCountView> toGenreBookCountView(List<GenreBookCountDTO> genreBookCountView);

    @Mapping(target = "genre", expression = "java(pair.getFirst().toString())")
    @Mapping(target = "avgLendings", expression = "java(pair.getSecond())")
    public abstract GenreAvgLendingsView toGenreAvgLendingsView(Pair<Genre, String> pair);

    public abstract List<GenreAvgLendingsView> toGenreAvgLendingsView(List<Pair<Genre, String>> pairList);
}
