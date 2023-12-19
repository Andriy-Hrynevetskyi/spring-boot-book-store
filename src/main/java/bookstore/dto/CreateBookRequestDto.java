package bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Title field can't be null or empty")
    @Size(max = 255)
    private String title;
    @NotBlank(message = "Author field can't be null or empty")
    @Size(max = 255)
    private String author;
    @NotBlank(message = "ISBN field can't be null or empty")
    @Pattern(regexp="\\d{13}", message = "isbn must contain 13 digits")
    private String isbn;
    @NotNull(message = "Price can't be null or less than 0")
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
}
