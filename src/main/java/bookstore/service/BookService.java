package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParameters;
import bookstore.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void updateBookById(Long id, CreateBookRequestDto requestDto);

    void deleteBookById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
