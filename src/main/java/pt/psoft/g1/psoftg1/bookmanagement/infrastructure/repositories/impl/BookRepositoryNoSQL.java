package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.bson.Document;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import pt.psoft.g1.psoftg1.authormanagement.dataschema.AuthorNoSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.genremanagement.dataschema.GenreNoSQL;
import pt.psoft.g1.psoftg1.shared.services.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.dataschema.BookNoSQL;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "books")
public class BookRepositoryNoSQL implements BookRepository {

    private final MongoTemplate mongoTemplate;

    public BookRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Book> findByGenre(String genre) {
        Query query = Query.query(Criteria.where("genre.name").is(genre));
        return mongoTemplate.find(query, BookNoSQL.class)
                .stream().map(BookNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitle(String title) {
        Query query = Query.query(Criteria.where("title.value").is(title));
        return mongoTemplate.find(query, BookNoSQL.class)
                .stream().map(BookNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorName").regex(authorName, "i"));
        List<BookNoSQL> books = mongoTemplate.find(query, BookNoSQL.class);
        return books.stream().map(BookNoSQL::toDomain).toList();
    }

    @Override
    @Cacheable(key = "#isbn")
    public Optional<Book> findByIsbn(String isbn) {
        Query query = new Query(Criteria.where("isbn").is(isbn));
        Book book = mongoTemplate.findOne(query, Book.class, "books");
        return Optional.ofNullable(book);
    }

    @Override
    public org.springframework.data.domain.Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(oneYearAgo)),
                Aggregation.group("book.$id").count().as("count"), // agrupa pelo ID do Book
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(5)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "lending", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        List<String> bookIds = mappedResults.stream()
                .map(doc -> doc.getString("_id"))
                .toList();

        Query bookQuery = new Query(Criteria.where("id").in(bookIds));
        List<Book> books = mongoTemplate.find(bookQuery, Book.class);

        Map<Isbn, Book> bookMap = books.stream()
                .collect(Collectors.toMap(Book::getIsbn, b -> b));

        List<BookCountDTO> dtoList = mappedResults.stream()
                .map(doc -> {
                    String bookId = doc.getString("_id");
                    Book book = bookMap.get(bookId);
                    Long count = doc.getLong("count");
                    return new BookCountDTO(book, count);
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }


    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        return mongoTemplate.find(Query.query(Criteria.where("authorNumber").is(authorNumber)), BookNoSQL.class)
                .stream()
                .map(BookNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooks(Page page, SearchBooksQuery query) {
        Query mongoQuery = new Query();

        if (query.getTitle() != null) {
            mongoQuery.addCriteria(Criteria.where("title.value").regex(query.getTitle(), "i"));
        }
        if (query.getAuthorName() != null) {
            mongoQuery.addCriteria(Criteria.where("authors.name.value").regex(query.getAuthorName(), "i"));
        }
        if (query.getGenre() != null) {
            mongoQuery.addCriteria(Criteria.where("genre.name").is(query.getGenre()));
        }

        return mongoTemplate.find(mongoQuery, BookNoSQL.class)
                .stream().map(BookNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        BookNoSQL entity = BookNoSQL.fromDomain(book);

        if (entity.getGenre() != null) {
            GenreNoSQL genre = mongoTemplate.findOne(
                    Query.query(Criteria.where("genre").is(entity.getGenre().getGenre())),
                    GenreNoSQL.class
            );
            if (genre != null) {
                entity.setGenre(genre);
            }
        }

        if (entity.getAuthors() != null && !entity.getAuthors().isEmpty()) {
            List<AuthorNoSQL> managedAuthors = entity.getAuthors().stream()
                    .map(a -> mongoTemplate.findOne(
                            Query.query(Criteria.where("name").is(a.getName())),
                            AuthorNoSQL.class
                    ))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            entity.setAuthors(managedAuthors);
        }

        if (entity.getId() == null || mongoTemplate.findById(entity.getId(), BookNoSQL.class) == null) {
            mongoTemplate.insert(entity);
        } else {
            mongoTemplate.save(entity);
        }

        return entity.toDomain();
    }

    @Override
    @CacheEvict(key = "#book.isbn")
    public void delete(Book book) {
        Query query = Query.query(Criteria.where("isbn.value").is(book.getIsbn().toString()));
        mongoTemplate.remove(query, BookNoSQL.class);
    }
}
