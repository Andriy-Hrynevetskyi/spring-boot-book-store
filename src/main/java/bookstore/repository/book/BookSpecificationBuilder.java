package bookstore.repository.book;

import bookstore.dto.BookSearchParameters;
import bookstore.model.Book;
import bookstore.repository.SpecificationBuilder;
import bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
//import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.prices() != null && searchParameters.prices().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("price")
                    .getSpecification(searchParameters.prices()));
        }
        return spec;

        /*return Optional.of(Specification.where(null))
                .map(spec -> addAuthorSpecification(spec, searchParameters))
                .map(spec -> addTitleSpecification(spec, searchParameters))
                .map(spec -> addPriceSpecification(spec, searchParameters))
                .orElse(Specification.where(null));
    }

    private Specification<Book> addPriceSpecification(
    Specification<Book> spec,
    BookSearchParameters searchParameters) {
        return Optional.ofNullable(searchParameters.prices())
                .filter(prices -> prices.length > 0)
                .map(prices -> spec.and(bookSpecificationProviderManager
                .getSpecificationProvider("price")
                .getSpecification(prices)))
                .orElse(spec);
    }

    private Specification<Book> addTitleSpecification(Specification<Book> spec,
    BookSearchParameters searchParameters) {
        return Optional.ofNullable(searchParameters.prices())
                .filter(titles -> titles.length > 0)
                .map(titles -> spec.and(bookSpecificationProviderManager
                .getSpecificationProvider("title")
                .getSpecification(titles)))
                .orElse(spec);
    }

    private Specification<Book> addAuthorSpecification(Specification<Book> spec,
    BookSearchParameters searchParameters) {
        return Optional.ofNullable(searchParameters.prices())
                .filter(authors -> authors.length > 0)
                .map(authors -> spec.and(bookSpecificationProviderManager
                .getSpecificationProvider("author")
                .getSpecification(authors)))
                .orElse(spec);*/
    }
}
