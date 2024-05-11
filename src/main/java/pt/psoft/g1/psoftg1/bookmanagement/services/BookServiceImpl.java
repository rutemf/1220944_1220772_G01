package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

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
	public Book create(CreateBookRequest request) throws Exception {
		Book newBook = null;

		try {
			if(findByIsbn(new Isbn(request.getIsbn())).isPresent()) {
				throw new Exception("A book with provided Isbn is already registered");
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

			Optional<Genre> genre = genreRepository.findByString(request.getGenre());
			if(genre.isEmpty()) {
				throw new Exception("Genre not found");
			}
			newBook = new Book(request.getIsbn(), request.getTitle(), request.getDescription(), genre.get(), authors);
		} catch(Exception e) {
			throw new Exception("One of the provided data does not match domain criteria: " + e.getMessage());
		}

        return newBook;
	}


	@Override
	public Book update(UpdateBookRequest request, String currentVersion) throws Exception {
		Book book = null;

		try {
			Optional<Book> tempBook = findByIsbn(new Isbn(request.getIsbn()));
			if(tempBook.isEmpty()) {
				throw new Exception("A book with provided Isbn was not found");
			}
			book = tempBook.get();
			if(request.getAuthors()!= null){
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

				request.setAuthorObjList(authors);
			}
			if(request.getGenre()!= null){
				Optional<Genre> genre = genreRepository.findByString(request.getGenre());
				if(genre.isEmpty()) {
					throw new Exception("Genre not found");
				}

				request.setGenreObj(genre.get());
			}

			book.applyPatch(Long.parseLong(currentVersion), request);
			bookRepository.save(book);
		} catch(Exception e) {
			throw new Exception("One of the provided data does not match domain criteria: " + e.getMessage());
		}

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

	@Override
	public Optional<Book> findByIsbn(Isbn isbn) {
		return this.bookRepository.findByIsbn(isbn.toString());
	}
}
