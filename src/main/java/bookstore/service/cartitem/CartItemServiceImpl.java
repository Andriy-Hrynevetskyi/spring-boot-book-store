package bookstore.service.cartitem;

import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.service.shoppingcart.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE =
            "Can't find cart item with given id doesn't exist: ";
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public ShoppingCartDto deleteCartItemById(Long cartItemId, Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartFromDb(userId);
        if (checkIfCartItemExist(shoppingCart, cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return shoppingCartService.updateShoppingCart(userId);
        }
        throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE + cartItemId);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItemById(Long cartItemId, Long userId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartFromDb(userId);
        if (checkIfCartItemExist(shoppingCart, cartItemId)) {
            CartItem cartItemFromShoppingCart = cartItemRepository
                    .findCartItemById(cartItemId).get();
            cartItemFromShoppingCart.setQuantity(cartItemFromShoppingCart.getQuantity() + quantity);
            cartItemRepository.save(cartItemFromShoppingCart);
            return shoppingCartService.updateShoppingCart(userId);
        }
        throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE + cartItemId);
    }

    private boolean checkIfCartItemExist(ShoppingCart shoppingCart, Long cartItemId) {
        return shoppingCart.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getId().equals(cartItemId));
    }
}
