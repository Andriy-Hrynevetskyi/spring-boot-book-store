package bookstore.service.book;

import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookSearchParameters;
import bookstore.dto.book.BookWithoutCategoryIds;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.model.Book;
import bookstore.repository.book.BookRepository;
import bookstore.repository.book.BookSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findBookById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a book with given id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void updateBookById(Long id, CreateBookRequestDto requestDto) {
        if (!bookRepository.existsBookById(id)) {
            throw new EntityNotFoundException("Can't find a book with given id: " + id);
        }
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> search(BookSearchParameters searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookWithoutCategoryIds> findAllByCategoryId(Pageable pageable, Long id) {
        return bookRepository.findAllByCategoryId(pageable, id).stream()
                .map(bookMapper::toBookWithoutCategoryId)
                .toList();
    }
}
