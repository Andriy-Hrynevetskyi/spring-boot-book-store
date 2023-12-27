package bookstore.service.category;

import bookstore.dto.category.CategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> getAll(Pageable pageable);
}
