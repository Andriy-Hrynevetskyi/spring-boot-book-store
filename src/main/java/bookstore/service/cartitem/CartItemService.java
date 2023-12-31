package bookstore.service.cartitem;

import bookstore.dto.shoppingcart.ShoppingCartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {

    ShoppingCartDto deleteCartItemById(Long cartItemId, Long id);

    ShoppingCartDto updateCartItemById(Long cartItemId, Long userId, int quantity);
}
