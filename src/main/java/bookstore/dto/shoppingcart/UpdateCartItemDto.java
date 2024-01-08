package bookstore.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemDto {
    @Min(value = 1, message = "Quantity can't be less then 0")
    private int quantity;
}
