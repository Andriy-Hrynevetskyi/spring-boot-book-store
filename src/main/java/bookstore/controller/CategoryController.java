package bookstore.controller;

import bookstore.dto.category.CategoryDto;
import bookstore.service.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.getAll(pageable);
    }
}

