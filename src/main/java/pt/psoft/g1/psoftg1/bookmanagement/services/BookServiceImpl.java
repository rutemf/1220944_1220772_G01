package pt.psoft.g1.psoftg1.book;

import java.util.Optional;

import jakarta.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final GenreRepository genreRepository;
	@Override
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}
	@Override
	public Optional<Book> findOne(final ISBN ISBN) {
		return bookRepository.findByISBN(ISBN);
	}

	@Override
	public Book create(final createBookRequest resource) {
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