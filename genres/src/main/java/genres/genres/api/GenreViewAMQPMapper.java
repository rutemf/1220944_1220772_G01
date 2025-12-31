package genres.genres.api;

import genres.genres.model.Genre;
import genres.shared.api.MapperInterface;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GenreViewAMQPMapper extends MapperInterface {

    @Mapping(target = "genre", source = "genre")

    public abstract GenreViewAMQP toGenreViewAMQP(Genre genre);

    public abstract List<GenreViewAMQP> toGenreViewAMQP(List<Genre> genreList);

}
