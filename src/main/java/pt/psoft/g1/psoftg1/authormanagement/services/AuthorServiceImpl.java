package pt.psoft.g1.psoftg1.author;

import java.util.Optional;

import jakarta.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;
	@Override
	public Iterable<Author> findAll() {
		return authorRepository.findAll();
	}
	@Override
	public Optional<Author> findOne(final AuthorNumber AuthorNumber) {
		return authorRepository.findByAuthorNumber(AuthorNumber);
	}

	@Override
	public Author create(final createAuthorRequest resource) {
		final Author author = EditAuthorMapper.create(resource);
		return authorRepository.save(author);
	}

	@Override
	public Author partialUpdate(final AuthorNumber authorNumber, final EditAuthorRequest resource, final long desiredVersion) {

		final Author author = barRepository.findByAuthorNumber(authorNumber)
				.orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));

		final Author author = authorRepository.findByName(resource.getAuthor())
				.orElseThrow(() -> new ValidationException("Select an existing foo"));

		author.applyPatch(desiredVersion, resource.getShortNote(), resource.getDescription(), author);

		return authorRepository.save(author);
	}

	/*DUVIDAS, FICA OU NAO?
	@Override
	public Author update(final AuthorNumber authorNumber, final EditAuthorRequest resource, final long desiredVersion) {
		// first let's check if the object exists so we don't create a new object with
		// save
		final Author author = authorRepository.findByAuthorNumber(authorNumber)
				.orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));

		// we need to search the related Foo object so we can reference it in the new
		// Bar
		final Author author = authorRepository.findByName(resource.getAuthor())
				.orElseThrow(() -> new ValidationException("Select an existing author"));

		// apply update
		author.updateData(desiredVersion, resource.getShortNote(), resource.getDescription(), author);

		return authorRepository.save(author);*/
	}
}
