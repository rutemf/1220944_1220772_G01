package pt.psoft.g1.psoftg1.authormanagement.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.EditAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private AuthorRepository authorRepository;
	@Override
	public Iterable<Author> findAll() {
		//return authorRepository.findAll();
		return null;
	}

	@Override
	public Optional<Author> findOne(Name name) {
		return Optional.empty();
	}

	@Override
	public Author partialUpdate(Long authornumber, EditAuthorRequest resource, long parseLong) {
		return null;
	}

	@Override
	public Author update(Long authornumber, EditAuthorRequest resource, long desiredVersion) {
		return null;
	}

	@Override
	public Optional<Author> findOne(final Long authorNumber) {
		//return authorRepository.findByAuthorNumber(authorNumber);
		return null;
	}

	@Override
	public Author create(final CreateAuthorRequest resource) {
		//final Author author = EditAuthorMapper.create(resource);
		//return authorRepository.save(author);
		return null;
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
