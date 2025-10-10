package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
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
        TypedQuery<BookSQL> query = entityManager.createQuery(
        "SELECT b FROM BookSQL b JOIN b.genre g WHERE g.genre LIKE :genre", BookSQL.class);

        query.setParameter("genre", "%" + genre + "%");
        return query.getResultList().stream().map(BookSQL::toDomain).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<BookSQL> query = entityManager.createQuery(
        "SELECT b FROM BookSQL b WHERE b.title LIKE :title", BookSQL.class);

        query.setParameter("title", "%" + title + "%");
        return query.getResultList().stream().map(BookSQL::toDomain).toList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        TypedQuery<BookSQL> query = entityManager.createQuery(
        "SELECT b FROM BookSQL b JOIN b.authors a WHERE a.name LIKE :authorName", BookSQL.class);

        query.setParameter("authorName", "%" + authorName + "%");
        return query.getResultList().stream().map(BookSQL::toDomain).toList();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        TypedQuery<BookSQL> query = entityManager.createQuery(
        "SELECT b FROM BookSQL b WHERE b.isbn.isbn = :isbn", BookSQL.class);

        query.setParameter("isbn", isbn);
        return query.getResultStream().map(BookSQL::toDomain).findFirst();
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        TypedQuery<BookSQL> query = entityManager.createQuery(
        "SELECT b FROM BookSQL b JOIN b.authors a WHERE a.authorNumber = :authorNumber", BookSQL.class);

        query.setParameter("authorNumber", authorNumber);
        return query.getResultList().stream().map(BookSQL::toDomain).toList();
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        return List.of();
    }

    @Override
    public Book save(Book book) {
        BookSQL entity = BookSQL.fromDomain(book);

        if (entityManager.find(BookSQL.class, entity.getId()) == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }

        return entity.toDomain();
    }

    @Override
    public void delete(Book book) {
        BookSQL entity = BookSQL.fromDomain(book);
        BookSQL managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managed);
    }
}
