package bookstore.service.category;

import bookstore.dto.category.CategoryDto;
import bookstore.dto.category.CategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto save(CategoryRequestDto requestDto);

    void updateCategoryById(Long id, CategoryRequestDto requestDto);
}
