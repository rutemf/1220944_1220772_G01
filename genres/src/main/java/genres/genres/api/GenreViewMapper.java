package genres.genres.api;

import genres.genres.model.Genre;
import genres.shared.api.MapperInterface;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GenreViewMapper extends MapperInterface {
    @Mapping(target = "genre", source = "genre")

    public abstract GenreView toGenreView(Genre book);

    public abstract List<GenreView> toGenreView(List<Genre> bookList);

}
