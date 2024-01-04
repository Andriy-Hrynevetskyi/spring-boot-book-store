package bookstore.service.shoppingcart;

import bookstore.dto.shoppingcart.AddToCartRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long id);
}
