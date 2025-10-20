package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import pt.psoft.g1.psoftg1.shared.services.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookNoSQL;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
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
        Query query = Query.query(Criteria.where("authors.name.value").is(authorName));
        return mongoTemplate.find(query, BookNoSQL.class)
                .stream().map(BookNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Query query = Query.query(Criteria.where("isbn.value").is(isbn));
        BookNoSQL bookNoSQL = mongoTemplate.findOne(query, BookNoSQL.class);
        return Optional.ofNullable(bookNoSQL).map(BookNoSQL::toDomain);
    }

    @Override
    public org.springframework.data.domain.Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("lendDate").gte(oneYearAgo)),

                Aggregation.group("book")
                        .count().as("lendingsCount")
                        .first("book").as("book"),

                Aggregation.sort(org.springframework.data.domain.Sort.Direction.DESC, "lendingsCount"),

                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<org.bson.Document> results = mongoTemplate.aggregate(aggregation, "lendings", org.bson.Document.class);

        List<BookCountDTO> topBooks = results.getMappedResults().stream().map(doc -> {
            Book book = mongoTemplate.getConverter().read(Book.class, (org.bson.Document) doc.get("book"));
            long count = ((Number) doc.get("lendingsCount")).longValue();
            return new BookCountDTO(book, count);
        }).toList();

        long totalCount = mongoTemplate.count(new Query(Criteria.where("lendDate").gte(oneYearAgo)), "lendings");

        return new org.springframework.data.domain.PageImpl<>(topBooks, pageable, totalCount);
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        Query query = Query.query(Criteria.where("authors.authorNumber").is(authorNumber));
        return mongoTemplate.find(query, BookNoSQL.class)
                .stream().map(BookNoSQL::toDomain).collect(Collectors.toList());
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
        BookNoSQL bookNoSQL = BookNoSQL.fromDomain(book);
        BookNoSQL saved = mongoTemplate.save(bookNoSQL);
        return saved.toDomain();
    }

    @Override
    public void delete(Book book) {
        Query query = Query.query(Criteria.where("isbn.value").is(book.getIsbn().toString()));
        mongoTemplate.remove(query, BookNoSQL.class);
    }
}
