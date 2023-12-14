package bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    private static final String TITLE_ERR_MSG = "Title field can't be null or empty";
    private static final String AUTHOR_ERR_MSG = "Author field can't be null or empty";
    private static final String ISBN_ERR_MSG = "ISBN field can't be null or empty";
    private static final String PRICE_ERR_MSG = "Price can't be null or less than 0";
    @NotBlank(message = TITLE_ERR_MSG)
    private String title;
    @NotBlank(message = AUTHOR_ERR_MSG)
    private String author;
    @NotBlank(message = ISBN_ERR_MSG)
    private String isbn;
    @NotNull(message = PRICE_ERR_MSG)
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
