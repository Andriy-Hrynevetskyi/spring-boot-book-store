package bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Name field cannot be empty or null")
    @Size(max = 255)
    private String name;
    private String description;
}
