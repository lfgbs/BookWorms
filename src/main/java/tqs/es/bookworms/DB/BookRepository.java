package tqs.es.bookworms.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.es.bookworms.Entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
