package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	private GenreRepository genreRepository;
	@Override
	public Book create(CreateBookRequest request) throws Exception {
		Book newBook = null;

		//This should be wrapped on a try catch to avoid any domain exceptions, this way we make sure we catch everything
		try {
			if(findByIsbn(new Isbn(request.getIsbn())) != null) {
				throw new Exception("A reader with provided Isbn is already registered");
			}

			//Find the authors for that book based on request.getAuthorName();
			//TODO: Instead of a string, an array of strings should be set on authorName to have more than just one author. A better solution would be to save AuthorNumbers to avoid object copies and keep simple references

			//newBook = new Book(request.getIsbn(), request.getTitle(), request.getDescription(), request.getGenre(), request.getAuthorName());

			//TODO: Authors need to be passed, in some way, to the book constructor, as it'll create the objects itself.
			newBook = new Book(request.getIsbn(), request.getTitle(), request.getDescription(), request.getGenre());
		} catch(Exception e) {
			throw new Exception("One of the provided data does not match domain criteria: " + e.getMessage());
		}

		return newBook;
	}

	@Override
	public Book save(Book book) throws Exception {
		return this.bookRepository.save(book);
	}

	@Override
	public Book findByIsbn(Isbn isbn) {
		return null;
	}
}
