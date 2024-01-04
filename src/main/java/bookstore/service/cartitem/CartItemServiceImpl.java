package bookstore.service.cartitem;

import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.ShoppingCart;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartDto deleteCartItemById(Long cartItemId, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(
                    () -> new EntityNotFoundException(
                        "Can't find shopping cart by given user id: " + userId)
        );
        boolean isItemInShoppingCart = shoppingCart.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getId().equals(cartItemId));
        if (isItemInShoppingCart) {
            cartItemRepository.deleteById(cartItemId);
            ShoppingCart updatedShoppingCart = shoppingCartRepository
                    .findShoppingCartByUserId(userId).get();
            return shoppingCartMapper.toDto(updatedShoppingCart);
        }
        throw new EntityNotFoundException("Cant item with given id doesn't exist: " + cartItemId);
    }
}
