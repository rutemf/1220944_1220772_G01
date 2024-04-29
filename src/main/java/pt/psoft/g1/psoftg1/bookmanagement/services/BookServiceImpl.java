package pt.psoft.g1.psoftg1.bookmanagement.services;

import java.util.Optional;

import jakarta.validation.ValidationException;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.EditBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;

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
			if(findByIsbn(request.getIsbn()) != null) {
				throw new Exception("A reader with provided Isbn is already registered");
			}

			newBook = new Book(request.getIsbn(), request.getTitle(), request.getDescription(), request.getGenre(), request.getAuthorName());
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
