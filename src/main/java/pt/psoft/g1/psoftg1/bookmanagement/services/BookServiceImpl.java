package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.*;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource({"classpath:config/library.properties"})
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final GenreRepository genreRepository;
	private final AuthorRepository authorRepository;
	private final PhotoRepository photoRepository;
	private final ReaderRepository readerRepository;

	@Value("${suggestionsLimitPerGenre}")
	private long suggestionsLimitPerGenre;

	@Override
	public Book create(CreateBookRequest request, String isbn) {
		Book newBook = null;

		if(bookRepository.findByIsbn(isbn).isPresent()) {
			throw new ConflictException("A book with provided Isbn is already registered");
		}

		List<Long> authorNumbers = request.getAuthors();
		List<Author> authors = new ArrayList<>();
		for (Long authorNumber : authorNumbers) {

			Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
			if(temp.isEmpty()) {
				continue;
			}

			Author author = temp.get();
			authors.add(author);
		}

		MultipartFile photo = request.getPhoto();
		String photoURI = request.getPhotoURI();
		if(photo == null && photoURI != null || photo != null && photoURI == null) {
			request.setPhoto(null);
			request.setPhotoURI(null);
		}

		final var genre = genreRepository.findByString(request.getGenre())
				.orElseThrow(() -> new NotFoundException("Genre not found"));

		newBook = new Book(isbn, request.getTitle(), request.getDescription(), genre, authors, photoURI);

        return bookRepository.save(newBook);
	}


	@Override
	public Book update(UpdateBookRequest request, String currentVersion) {
		Book book;

		Optional<Book> tempBook = findByIsbn(request.getIsbn());
		if(tempBook.isEmpty()) {
			throw new NotFoundException("A book with provided Isbn was not found");
		}
        book = tempBook.get();
        if(request.getAuthors()!= null) {
            List<Long> authorNumbers = request.getAuthors();
            List<Author> authors = new ArrayList<>();
            for (Long authorNumber : authorNumbers) {
                Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
                if (temp.isEmpty()) {
                    //TODO: É suposto passar à frente e ignorar os que não foram encontrados ou damos erro?
                    continue;
                }
                Author author = temp.get();
                authors.add(author);
            }

            request.setAuthorObjList(authors);
        }

		MultipartFile photo = request.getPhoto();
		String photoURI = request.getPhotoURI();
		if(photo == null && photoURI != null || photo != null && photoURI == null) {
			request.setPhoto(null);
			request.setPhotoURI(null);
		}

        if (request.getGenre() != null) {
            Optional<Genre> genre = genreRepository.findByString(request.getGenre());
            if (genre.isEmpty()) {
                throw new NotFoundException("Genre not found");
            }
            request.setGenreObj(genre.get());
        }

        book.applyPatch(Long.parseLong(currentVersion), request);

		bookRepository.save(book);


		return book;
	}

	@Override
	public Book save(Book book) {
		return this.bookRepository.save(book);
	}

	@Override
	public List<BookCountDTO> findTop5BooksLent(){
		LocalDate oneYearAgo = LocalDate.now().minusYears(1);
		Pageable pageableRules = PageRequest.of(0,5);
		return this.bookRepository.findTop5BooksLent(oneYearAgo, pageableRules).getContent();
	}

	@Override
	public Optional<Book> removeBookPhoto(String isbn, long desiredVersion) {
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new NotFoundException("Cannot find reader"));

		String photoFile = book.getPhoto().getPhotoFile();
		book.removePhoto(desiredVersion);
		Optional<Book> updatedBook = Optional.of(bookRepository.save(book));
		photoRepository.deleteByPhotoFile(photoFile);
		return updatedBook;
	}

	@Override
	public List<Book> findByGenre(String genre) {
		return this.bookRepository.findByGenre(genre.toString());
	}

	public List<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	public Optional<Book> findByIsbn(String isbn) {
		return this.bookRepository.findByIsbn(isbn);
	}

	public List<Book> getBooksSuggestionsForReader(String readerNumber) {
		List<Book> books = new ArrayList<>();

		ReaderDetails readerDetails = readerRepository.findByReaderNumber(readerNumber).orElseThrow(() -> new NotFoundException("Reader not found with provided login"));
		List<Genre> interestList = readerDetails.getInterestList();

		if(interestList.isEmpty()) {
			throw new NotFoundException("Reader has no interests");
		}

		for(Genre genre : interestList) {
			List<Book> tempBooks = bookRepository.findByGenre(genre.toString());
			if(tempBooks.isEmpty()) {
				continue;
			}

			long genreBookCount = 0;

            for (Book loopBook : tempBooks) {
                if (genreBookCount >= suggestionsLimitPerGenre) {
                    break;
                }

                books.add(loopBook);
				genreBookCount++;
            }
		}

		return books;
	}
}
