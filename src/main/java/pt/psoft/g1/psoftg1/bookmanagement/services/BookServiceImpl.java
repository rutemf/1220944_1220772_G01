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
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;

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

			//Find the authors for that book based on request.getAuthorName();
			//TODO: Instead of a string, an array of strings should be set on authorName to have more than just one author. A better solution would be to save AuthorNumbers to avoid object copies and keep simple references

			//newBook = new Book(request.getIsbn(), request.getTitle(), request.getDescription(), request.getGenre(), request.getAuthorName());

			//TODO: Authors need to be passed, in some way, to the book constructor, as it'll create the objects itself.
			List<Long> authorNumbers = request.getAuthors();
			int authorNumberCount = authorNumbers.size();
			List<Author> authors = new ArrayList<>();
            for (Long authorNumber : authorNumbers) {
                Author author = authorRepository.searchByAuthorNumber(authorNumber);
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
	public Book update(UpdateBookRequest request) throws Exception {
		Book book = null;

		try {
			Optional<Book> tempBook = findByIsbn(new Isbn(request.getIsbn()));
			if(tempBook.isEmpty()) {
				throw new Exception("A book with provided Isbn was not found");
			}
			book = tempBook.get();
			//Find the authors for that book based on request.getAuthorName();
			List<Long> authorNumbers = request.getAuthors();
			int authorNumberCount = authorNumbers.size();
			List<Author> authors = new ArrayList<>();
			for (Long authorNumber : authorNumbers) {
				Author author = authorRepository.searchByAuthorNumber(authorNumber);
				authors.add(author);
			}

			request.setAuthorObjList(authors);

			//TODO: Authors need to be passed, in some way, to the book constructor, as it'll create the objects itself.
			Optional<Genre> genre = genreRepository.findByString(request.getGenre());
			if(genre.isEmpty()) {
				throw new Exception("Genre not found");
			}
			request.setGenreObj(genre.get());
			bookRepository.update(request.getIsbn(), request.getTitle(), request.getDescription(), request.getGenre(), authors);
			book.applyPatch(request);
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
	public Optional<Book> findByIsbn(Isbn isbn) {
		return this.bookRepository.findByIsbn(isbn.toString());
	}
}
