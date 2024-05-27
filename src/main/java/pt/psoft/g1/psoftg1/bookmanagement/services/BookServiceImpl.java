package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.model.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final GenreRepository genreRepository;
	private final AuthorRepository authorRepository;
	@Override
	public Book create(CreateBookRequest request) {
		Book newBook = null;


		if(findByIsbn(new Isbn(request.getIsbn())).isPresent()) {
			throw new ConflictException("A book with provided Isbn is already registered");
		}

		List<Long> authorNumbers = request.getAuthors();
		List<Author> authors = new ArrayList<>();
		for (Long authorNumber : authorNumbers) {

			Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
			if(temp.isEmpty()) {
				//TODO: É suposto passar à frente e ignorar os que não foram encontrados ou damos erro?
				continue;
			}

			Author author = temp.get();
			authors.add(author);
		}
		final var genre = genreRepository.findByString(request.getGenre())
				.orElseThrow(() -> new NotFoundException("Genre not found"));

		newBook = new Book(request.getIsbn(), request.getTitle(), request.getDescription(), genre, authors);


        return newBook;
	}


	@Override
	public Book update(UpdateBookRequest request, String currentVersion) {
		Book book = null;

		Optional<Book> tempBook = findByIsbn(new Isbn(request.getIsbn()));
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
	public List<Book> findByGenre(Genre genre) {
		return this.bookRepository.findByGenre(genre.toString());
	}

	public List<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	@Override
	public Optional<Book> findByIsbn(Isbn isbn) {
		return this.bookRepository.findByIsbn(isbn.toString());
	}
}
