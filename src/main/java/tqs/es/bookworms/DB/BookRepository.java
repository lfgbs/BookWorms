package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Book;
import tqs.es.bookworms.Entities.BookStatus;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByStatusAndLocationId(BookStatus status, Long locationId);
    List<Book> findDistinctTitleByStatus(BookStatus status);
    List<Book> findTop10ByTitle(String title);
    Integer countBooksByTitleAndStatus(String title, BookStatus status);

}
