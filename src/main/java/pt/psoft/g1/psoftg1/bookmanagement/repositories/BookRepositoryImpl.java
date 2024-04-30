package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository{
    private List<Book> bookList = new ArrayList<>();

//    @Override
//    public Book findByTitle(Title title) {
//        for(int i = 0; i < title.size(); i++) {
//            Book book = bookList.get(i);
//            if(book.getTitle().toString().equals(title.toString())) {
//
//            }
//        }
//        return null;
//    }


    @Override
    public Iterable<Book> findAll() {
        return null;
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        String filterGenre = genre.toString();
        List<Book> filteredList = new ArrayList<>();

        for (Book book : bookList) {
            String bookGenre = book.getGenre().toString();
            if (bookGenre.equals(filterGenre) || bookGenre.contains(filterGenre)) {
                filteredList.add(book);
            }
        }

        return filteredList;
    }

    @Override
    public Book findByIsbn(Isbn isbn) {
        for (Book book : bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }


    @Override
    public Book save(Book book) throws Exception {
        try {
            if(findByIsbn(book.getIsbn()) != null) {
                throw new Exception("A book with provided isbn is already registered");
            }
            if(book.getGenre() != null) {
                throw new Exception("A book cannot be created without a genre");
            }
            if(book.getTitle() != null) {
                throw new Exception("A book cannot be created without a title");
            }

            bookList.add(book);
        } catch(Exception e) {
            throw new Exception("Unable to save given Reader: " + e.getMessage());
        }

        return book;
    }

    public Book update(Isbn isbn, UpdateBookRequest request) {
        String searchIsbn = isbn.toString();

        for(int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            String bookIsbn = book.getIsbn().toString();

            if(!searchIsbn.equals(bookIsbn)) {
                continue;
            }

            //TODO: As the object is a reference, it should be modified on the list. Nonetheless, this has to be tested with postman
            book.applyPatch(request);
            return book;
        }

        return null;
    }
}
