package pt.psoft.g1.psoftg1.bookmanagement.services;


import org.hibernate.validator.constraints.ISBN;
import pt.psoft.g1.psoftg1.CreateBookRequest;
import pt.psoft.g1.psoftg1.EditBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import java.util.Optional;

public interface BookService {

    /**
     *
     * @return
     */
    Iterable<Book> findAll();

    /**
     *
     * @param isbn
     * @return
     */
    Optional<Book> findOne(ISBN isbn);

    Optional<Book> findOne(Isbn isbn);

    /**
     * Creates a new Foo with the specified id.
     *
     * @param id
     * @param resource
     * @return
     */
    Book create(CreateBookRequest resource);

    /**
     * Updates (fully replaces) an existing Foo.
     *
     * @param id
     * @param resource
     * @param desiredVersion
     * @return
     */
    Book update(ISBN isbn, EditBookRequest resource, long desiredVersion);

    /**
     * Partial updates an existing Foo.
     *
     * @param id
     * @param resource  "patch document"
     * @param parseLong
     * @return
     */
    Book partialUpdate(ISBN isbn, EditBookRequest resource, long parseLong);

}
