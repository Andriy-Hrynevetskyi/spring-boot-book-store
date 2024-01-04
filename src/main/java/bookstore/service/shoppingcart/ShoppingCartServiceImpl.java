package bookstore.service.shoppingcart;

import bookstore.dto.shoppingcart.AddToCartRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
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
                () -> new EntityNotFoundException("Can't find a book with given id: " + bookId));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find an user with given id: " + userId)
        );
        ShoppingCart shoppingCartFromDb = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUser(user);
                    shoppingCartRepository.save(shoppingCart);
                    return shoppingCart;
                });
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setShoppingCart(shoppingCartFromDb);
        cartItemRepository.save(cartItem);
        shoppingCartFromDb.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartFromDb);
    }
}
