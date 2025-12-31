package books.books.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import books.books.model.Book;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCountDTO {
    private Book book;
    private long lendingCount;
}
