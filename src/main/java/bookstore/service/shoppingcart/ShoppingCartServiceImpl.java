package bookstore.service.shoppingcart;

import bookstore.dto.shoppingcart.AddToCartRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.exception.CartItemException;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.book.BookRepository;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import bookstore.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long userId) {
        Long bookId = requestDto.getBookId();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Can't find the book with given id: " + bookId));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find the user with given id: " + userId)
        );
        ShoppingCart shoppingCartFromDb = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUser(user);
                    shoppingCartRepository.save(shoppingCart);
                    return shoppingCart;
                });
        boolean isBookInShoppingCart = shoppingCartFromDb.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getBook().getId().equals(bookId));
        if (isBookInShoppingCart) {
            throw new CartItemException("Current book is already in your cart."
                    + "Please update quantity");
        }
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setShoppingCart(shoppingCartFromDb);
        cartItemRepository.save(cartItem);
        shoppingCartFromDb.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartFromDb);
    }

    @Override
    public ShoppingCartDto getShoppingCartByUserId(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no such shopping cart with given id: " + id)
        );
        return shoppingCartMapper.toDto(shoppingCart);
    }

    public ShoppingCart getShoppingCartFromDb(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find shopping cart by given user id: " + userId)
                );
    }

    @Transactional
    public ShoppingCartDto updateShoppingCart(Long userId) {
        ShoppingCart updatedShoppingCart = shoppingCartRepository
                .findShoppingCartByUserId(userId).get();
        return shoppingCartMapper.toDto(updatedShoppingCart);
    }
}
