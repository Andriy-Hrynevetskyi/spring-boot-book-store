package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookWithoutCategoryIds;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.model.Book;
import bookstore.model.Category;
import bookstore.repository.category.CategoryRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {

    BookWithoutCategoryIds toBookWithoutCategoryId(Book book);

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (!book.getCategories().isEmpty()) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book,
                               CreateBookRequestDto requestDto,
                               @Context CategoryRepository categoryRepository) {
        Set<Category> categories = requestDto.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id).get())
                .collect(Collectors.toSet());

        book.setCategories(categories);
    }
}
