package bookstore.service.cartitem;

import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE =
            "Can't find cart item with given id doesn't exist: ";
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartDto deleteCartItemById(Long cartItemId, Long userId) {
        ShoppingCart shoppingCart = getShoppingCartFromDb(userId);
        if (checkIfCartItemExist(shoppingCart, cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return updateShoppingCart(userId);
        }
        throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE + cartItemId);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateCartItemById(Long cartItemId, Long userId, int quantity) {
        ShoppingCart shoppingCart = getShoppingCartFromDb(userId);
        if (checkIfCartItemExist(shoppingCart, cartItemId)) {
            CartItem cartItemFromShoppingCart = cartItemRepository
                    .findCartItemById(cartItemId).get();
            cartItemFromShoppingCart.setQuantity(cartItemFromShoppingCart.getQuantity() + quantity);
            cartItemRepository.save(cartItemFromShoppingCart);
            return updateShoppingCart(userId);
        }
        throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE + cartItemId);
    }

    private ShoppingCart getShoppingCartFromDb(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find shopping cart by given user id: " + userId)
                );
    }

    private boolean checkIfCartItemExist(ShoppingCart shoppingCart, Long cartItemId) {
        return shoppingCart.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getId().equals(cartItemId));
    }

    private ShoppingCartDto updateShoppingCart(Long userId) {
        ShoppingCart updatedShoppingCart = shoppingCartRepository
                .findShoppingCartByUserId(userId).get();
        return shoppingCartMapper.toDto(updatedShoppingCart);
    }
}
