package pt.psoft.g1.psoftg1.bookmanagement.services;

import java.util.Optional;

import jakarta.validation.ValidationException;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.CreateBookRequest;
import pt.psoft.g1.psoftg1.EditBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	private GenreRepository genreRepository;
	@Override
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findOne(ISBN isbn) {
		return Optional.empty();
	}

	@Override
	public Optional<Book> findOne(final Isbn isbn) {
		return bookRepository.findByISBN(isbn);
	}

	@Override
	public Book create(final CreateBookRequest resource) {
		// construct a new object based on data received by the service
		final Book book = EditBookMapper.create(resource);

		// TODO ensure domain invariants or does the Mapper runs validations?
		return bookRepository.save(book);
	}

	@Override
	public Book update(final ISBN isbn, final EditBookRequest resource, final long desiredVersion) {
		// first let's check if the object exists so we don't create a new object with
		// save
		final Book book = bookRepository.findByISBN(isbn)
				.orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));

		// we need to search the related Foo object so we can reference it in the new
		// Bar
		final Book book = bookRepository.findByName(resource.getBook())
				.orElseThrow(() -> new ValidationException("Select an existing book"));

		// apply update
		book.updateData(desiredVersion, resource.getShortNote(), resource.getDescription(), book);

		return bookRepository.save(book);
	}

	@Override
	public Book partialUpdate(final ISBN isbn, final EditBookRequest resource, final long desiredVersion) {
		// first let's check if the object exists so we don't create a new object with
		// save
		final Book book = barRepository.findByISBN(isbn)
				.orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));

		// we need to search the related Foo object so we can reference it in the new
		// Bar
		final Book book = bookRepository.findByName(resource.getBook())
				.orElseThrow(() -> new ValidationException("Select an existing foo"));

		// since we got the object from the database we can check the version in memory
		// and apply the patch
		book.applyPatch(desiredVersion, resource.getShortNote(), resource.getDescription(), book);

		// in the meantime some other user might have changed this object on the
		// database, so concurrency control will still be applied when we try to save
		// this updated object
		return bookRepository.save(book);
	}
}
