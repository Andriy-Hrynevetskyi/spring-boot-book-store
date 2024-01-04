package bookstore.service.cartitem;

import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.repository.cartitem.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto deleteBookById(Long id) {
        //return cartItemRepository.deleteById(id);
        return null;
    }
}
