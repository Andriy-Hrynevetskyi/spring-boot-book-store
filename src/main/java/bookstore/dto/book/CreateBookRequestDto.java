package bookstore.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
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
    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?<gs1>\\d{3})"
            + "(?:(?<number>\\d{9})|"
            + "(?=[\\d -]{14}$)[ -](?<registrationGroup>\\d{1,5})[ -](?<registrant>\\d{1,7})[ -]"
            + "(?<publication>\\d{1,6})[ -])(?<checkDigit>\\d)$",
            message = "invalid isbn")
    private String isbn;
    @NotNull(message = "Price can't be null or less than 0")
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoryIds;
}
