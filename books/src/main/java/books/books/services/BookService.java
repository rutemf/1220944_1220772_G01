package books.books.services;

import books.books.api.BookViewAMQP;
import books.books.model.Book;
import books.shared.services.Page;

import java.util.List;

/**
 *
 */
public interface BookService {

    Book create(CreateBookRequest request, String isbn); // REST request

    Book create(BookViewAMQP bookViewAMQP); // AMQP request

    Book findByIsbn(String isbn);

    Book update(UpdateBookRequest request, Long currentVersion);

    Book update(BookViewAMQP bookViewAMQP);

    List<Book> findByGenre(String genre);

    List<Book> findByTitle(String title);

    List<Book> findByAuthorName(String authorName);

    Book removeBookPhoto(String isbn, long desiredVersion);

    List<Book> searchBooks(Page page, SearchBooksQuery query);
}
