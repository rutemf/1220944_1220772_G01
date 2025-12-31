package books.books.publishers;

import books.books.api.BookViewAMQP;
import books.books.model.Book;

public interface BookEventsPublisher {

    BookViewAMQP sendBookCreated(Book book);

    BookViewAMQP sendBookUpdated(Book book, Long currentVersion);

    BookViewAMQP sendBookDeleted(Book book, Long currentVersion);
}
