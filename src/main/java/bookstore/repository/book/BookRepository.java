package bookstore.repository.book;

import bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsBookById(Long id);

    @EntityGraph(attributePaths = "book.categories")
    Optional<Book> findBookById(Long id);

    @EntityGraph(attributePaths = "book.categories")
    List<Book> findAll(Specification<Book> bookSpecification);

    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :id")
    List<Book> findAllByCategoryId(Pageable pageable, Long id);
}
