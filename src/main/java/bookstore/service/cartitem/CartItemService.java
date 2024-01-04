package bookstore.service.cartitem;

import bookstore.dto.shoppingcart.ShoppingCartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    ShoppingCartDto deleteBookById(Long id);
}
