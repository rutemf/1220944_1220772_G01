package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.exists;

public class GenreRepositoryImpl implements GenreRepository{
    private List<Genre> genreList = new ArrayList<>();

    @Override
    public List<Genre> findByName(Genre genre) {
        return List.of();
    }

    @Override
    public List<Genre> findAll() {
        return genreList;
    }

    private boolean exists(Genre genre) {
        for (Genre g : genreList) {
            if (g.toString().equals(genre.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Genre save(Genre genre) throws Exception {
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be null.");
        }

        if (exists(genre)) {
            throw new Exception("A genre with the name '" + genre.toString() + "' is already registered.");
        }

        try {
            genreList.add(genre);
        } catch (Exception e) {
            throw new Exception("Unable to save the genre: " + e.getMessage(), e);
        }

        return genre;
    }
}
