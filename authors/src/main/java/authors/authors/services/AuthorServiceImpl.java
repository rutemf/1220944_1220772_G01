package authors.authors.services;

import authors.authors.api.AuthorsViewAMQP;
import authors.authors.model.Author;
import authors.authors.publishers.AuthorEventsPublisher;
import authors.authors.repositories.AuthorRepository;
import authors.exceptions.ConflictException;
import authors.exceptions.NotFoundException;
import authors.shared.repositories.PhotoRepository;
import authors.shared.services.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PhotoRepository photoRepository;
    private final AuthorEventsPublisher authorEventsPublisher;

    @Override
    public Author create(CreateAuthorRequest request) {
        final String name = request.getName();
        final String bio = request.getBio();
        final String photoURI = request.getPhotoURI();

        Author savedAuthor = create(name, bio, photoURI);

        if (savedAuthor != null) {
            authorEventsPublisher.sendAuthorCreated(savedAuthor);
        }

        return savedAuthor;    }

    @Override
    public Author create(AuthorsViewAMQP authorViewAMQP) {
        final String name = authorViewAMQP.getName();
        final String bio = authorViewAMQP.getBio();
        final String photoURI = null;

        return create(name, bio, photoURI);
    }

    private Author create(String name, String bio, String photoURI) {
        if (authorRepository.findByAuthorName(name).isPresent()) {
            throw new ConflictException("Author with name " + name + " already exists");
        }

        Author newAuthor = new Author(name, bio, photoURI);
        return authorRepository.save(newAuthor);
    }

    @Override
    public Author findByAuthorNumber(Long authorNumber) {
        return authorRepository.findByAuthorNumber(authorNumber)
                .orElseThrow(() -> new NotFoundException(Author.class, String.valueOf(authorNumber)));
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByAuthorName(name)
                .orElseThrow(() -> new NotFoundException(Author.class, name));
    }

    @Override
    public Author update(UpdateAuthorRequest request, Long currentVersion) {
        Author author = findByAuthorNumber(request.getAuthorNumber());
        String name = String.valueOf(request.getName());
        String bio = String.valueOf(request.getBio());
        MultipartFile photo = request.getPhoto();
        String photoURI = request.getPhotoURI();

        if (photo == null && photoURI != null || photo != null && photoURI == null) {
            photoURI = null;
        }

        author.applyPatch(currentVersion, name, bio, photoURI);

        Author updatedAuthor = authorRepository.save(author);

        if (updatedAuthor != null) {
            authorEventsPublisher.sendAuthorUpdated(updatedAuthor, currentVersion);
        }

        return updatedAuthor;    }

    @Override
    public Author update(AuthorsViewAMQP authorViewAMQP) {
        Long version = authorViewAMQP.getVersion();
        Long authorNumber = authorViewAMQP.getAuthorNumber();
        String name = authorViewAMQP.getName();
        String bio = authorViewAMQP.getBio();
        String photoURI = null;

        Author author = findByAuthorNumber(authorNumber);

        author.applyPatch(version, name, bio, photoURI);

        return authorRepository.save(author);    }

    @Override
    public List<Author> searchAuthors(Page page, SearchAuthorsQuery query) {
        if (page == null) {
            page = new Page(1, 10);
        }
        if (query == null) {
            query = new SearchAuthorsQuery("", null);
        }
        return authorRepository.searchAuthors(page, query);
    }

    @Override
    public Author removeAuthorPhoto(Long authorNumber, long desiredVersion) {
        Author author = findByAuthorNumber(authorNumber);

        String photoFile;
        try {
            assert author.getPhoto() != null;
            photoFile = author.getPhoto().getPhotoFile();
        } catch (NullPointerException e) {
            throw new NotFoundException("Author did not have a photo assigned to it.");
        }

        author.removePhoto(desiredVersion);

        Author deletedAuthor = authorRepository.save(author);
        if (deletedAuthor != null) {
            photoRepository.deleteByPhotoFile(photoFile);
            authorEventsPublisher.sendAuthorDeleted(deletedAuthor, desiredVersion);
        }

        return deletedAuthor;    }
}
