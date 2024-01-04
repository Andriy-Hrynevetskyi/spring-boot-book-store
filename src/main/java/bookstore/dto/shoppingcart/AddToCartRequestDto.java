package bookstore.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequestDto {
    @NotNull(message = "Book id cannot be null")
    private Long bookId;
    @Min(value = 1, message = "You have to order at least 1 book")
    private int quantity;
}
