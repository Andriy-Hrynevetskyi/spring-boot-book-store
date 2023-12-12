package bookstore.repository.book;

import bookstore.model.Book;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsBookById(Long id);

    List<Book> findAll(Specification<Book> bookSpecification);
}
