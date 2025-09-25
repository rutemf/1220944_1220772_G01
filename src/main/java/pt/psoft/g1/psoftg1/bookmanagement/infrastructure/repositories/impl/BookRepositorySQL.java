package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class BookRepositorySQL implements BookRepository {

    private final EntityManager entityManager;

    public BookRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> findByGenre(String genre) {
        TypedQuery<Book> query = entityManager.createQuery(
        "SELECT b FROM Book b JOIN b.genre g WHERE g.genre LIKE :genre", Book.class);

        query.setParameter("genre", "%" + genre + "%");
        return query.getResultList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> query = entityManager.createQuery(
        "SELECT b FROM Book b WHERE b.title.title LIKE :title", Book.class);

        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        TypedQuery<Book> query = entityManager.createQuery(
        "SELECT b FROM Book b JOIN b.authors a WHERE a.name.name LIKE :authorName", Book.class);

        query.setParameter("authorName", "%" + authorName + "%");
        return query.getResultList();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        TypedQuery<Book> query = entityManager.createQuery(
        "SELECT b FROM Book b WHERE b.isbn.isbn = :isbn", Book.class);

        query.setParameter("isbn", isbn);
        return query.getResultStream().findFirst();
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        TypedQuery<Book> query = entityManager.createQuery(
        "SELECT b FROM Book b JOIN b.authors a WHERE a.authorNumber = :authorNumber", Book.class);

        query.setParameter("authorNumber", authorNumber);
        return query.getResultList();
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        return List.of();
    }

    @Override
    public Book save(Book book) {
        if (book.getIsbn() == null || entityManager.find(Book.class, book.getIsbn()) == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void delete(Book book) {
        Book managed = entityManager.contains(book) ? book : entityManager.merge(book);
        entityManager.remove(managed);
    }
}
