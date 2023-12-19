package bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})\n" +
            "[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)\n" +
            "(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "invalid isbn")
    private String isbn;
    @NotNull(message = "Price can't be null or less than 0")
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
}
