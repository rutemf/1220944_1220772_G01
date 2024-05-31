package pt.psoft.g1.psoftg1.bookmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.model.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookViewMapper extends MapperInterface {
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "description", source = "description")
    //@Mapping(target = "description", expression = "java(mapOpt(book.getDescription()).toString())")
    @Mapping(target = "title", source = "title")
    public abstract BookView toBookView(Book book);

    public abstract List<BookView> toBookView(List<Book> bookList);

    @Mapping(target = "bookView", source = "book")
    public abstract BookCountView toBookCountView(BookCountDTO bookCountView);

    public abstract List<BookCountView> toBookCountView(List<BookCountView> bookCountView);

    public abstract List<BookCountView> toBookCountViewList(List<BookCountDTO> bookCountDTOList);

    public String[] map(List<Author> authorList) {
        String[] authorNames = new String[authorList.size()];
        for(int i = 0; i < authorList.size(); i++) {
            authorNames[i] = authorList.get(i).getName().toString();
        }

        return authorNames;
    }
}
