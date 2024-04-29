package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Genre save(Genre genre) {
        return null;
    }
    /*
    @Override
    public Genre save(Genre genre) throws Exception {
        try {
            if(findByIsbn(genre.get()) != null) {
                throw new Exception("A book with provided isbn is already registered");
            }

            bookList.add(book);
        } catch(Exception e) {
            throw new Exception("Unable to save given Reader: " + e.getMessage());
        }

        return genre;
    }*/
}
