package bookstore.dto.shoppingcart;

import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long bookId;
    private int quantity;
}
