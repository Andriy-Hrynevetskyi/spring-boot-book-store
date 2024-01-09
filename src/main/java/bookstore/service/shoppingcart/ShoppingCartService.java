package bookstore.service.shoppingcart;

import bookstore.dto.shoppingcart.AddToCartRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long id);

    ShoppingCartDto getShoppingCartByUserId(Long id);

    ShoppingCart getShoppingCartFromDb(Long userId);

    ShoppingCartDto updateShoppingCart(Long userId);
}
