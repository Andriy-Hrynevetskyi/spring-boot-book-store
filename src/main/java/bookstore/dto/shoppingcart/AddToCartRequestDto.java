package bookstore.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddToCartRequestDto {
    @NotNull(message = "Book id cannot be null")
    @Positive(message = "id value cannot be less then 0")
    private Long bookId;
    @Min(value = 1, message = "You have to order at least 1 book")
    private int quantity;
}
