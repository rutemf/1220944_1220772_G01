package genres.genres.publishers;

import genres.genres.api.GenreViewAMQP;
import genres.genres.model.Genre;

public interface GenreEventsPublisher {

    GenreViewAMQP sendGenreCreated(Genre genre);
    GenreViewAMQP sendGenreUpdated(Genre genre, Long currentVersion);
    GenreViewAMQP sendGenreDeleted(Genre genre, Long currentVersion);
}
