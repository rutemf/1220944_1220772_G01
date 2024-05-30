# Rest Mapping


| Method | URI Template                                                                    | Equivalent RPC operation | Access                  |                                       
|:-------|:--------------------------------------------------------------------------------|--------------------------|-------------------------|
| POST   | api/readers                                                                     | createReader             | *Anon*                  |
| GET    | api/readers?name={name}                                                         | getReadersByName         | Librarian               |
| GET    | api/readers/{year}/{seq}                                                        | getReader                | Librarian, Reader(Self) |
| PATCH  | api/readers/{year}/{seq}                                                        | updateReader             | Reader(Self)            |
| GET    | api/readers/{year}/{seq}/lendings?isbn={isbn}                                   | getReaderLendingsByIsbn  | Reader(Self)            |
| POST   | api/authors                                                                     | createAuthor             | Librarian               |
| GET    | api/authors?name={name}                                                         | searchAuthorsByName      | Librarian, Reader       |
| GET    | api/authors/{id}                                                                | getAuthor                | Librarian, Reader       |
| PATCH  | api/authors/{id}                                                                | updateAuthor             | Librarian               |
| GET    | api/books/{isbn}                                                                | getBook                  | Librarian, Reader       |
| PUT    | api/books/{isbn}                                                                | createBook               | Librarian               |
| PATCH  | api/books/{isbn}                                                                | updateBook               | Librarian               |
| GET    | api/books?genre={genre}                                                         | getBooksByGenre          | Librarian, Reader       |
| POST   | api/lendings                                                                    | createLending            | Librarian               |
| GET    | api/lendings/{year}/{seq}                                                       | getLending               | Librarian, Reader(Self) |
| PATCH  | api/lendings/{year}/{seq}                                                       | updateLending            | Reader(Self)            |
|        | PHASE 2                                                                         |                          |                         |
| GET    | api/books/?author={author} (falta resp prof)                                    | getBookByAuthor          | Reader                  |
| GET    | api/authors/{id}/coauthors (falta resp prof)                                    | getAuthorCoauthors       | Reader                  |
| GET    | api/authors/top5                                                                | getTop5Authors           | Reader                  |
| GET    | api/books/?title={title}                                                        | searchBookByTitle        | Reader                  |
| GET    | api/books/top5                                                                  | getTop5Books             | Librarian               |
| GET    | api/genres/top5                                                                 | getTop5Genres            | Librarian               |
| GET    | api/readers/top5                                                                | getTop5Readers           | Librarian               |
| GET    | api/books/suggestions                                                           | getBookSuggestions       | Reader(Self)            |
| GET    | api/lendings/overdue                                                            | getOverdueLendings       | Librarian               |
| GET    | api/lendings/avgduration                                                        | getAverageDuration       | Librarian               |
| GET    | api/lendings/?period={month}&count={12}                                         | getLendingCount          | Librarian               |
| GET    | api/genres/top5readers (falta resp prof)                                        | getTop5Readers           | Librarian               |
| GET    | api/lendings/avgduration?period={month}&groupby={genre}&begin={date}&end={date} | getAverageDuration       | Librarian               |

[//]: # (<img src="RestMapping-PSOFT_G1_Rest_Mapping.svg" alt="Rest Mapping Diagram">)