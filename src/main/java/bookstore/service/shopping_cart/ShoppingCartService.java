package bookstore.service.shopping_cart;

import bookstore.dto.shopping_cart.AddToCartRequestDto;
import bookstore.dto.shopping_cart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long id);
}
