package bookstore.service.order;

import bookstore.dto.order.OrderDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.exception.OrderUpdateException;
import bookstore.mapper.OrderMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.order.OrderRepository;
import bookstore.repository.orderitem.OrderItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import bookstore.repository.user.UserRepository;
import bookstore.service.shoppingcart.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public OrderDto addOrder(Long userId, String address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user with given id: " + userId));
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart with given id: " + userId));
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            BigDecimal itemPrice = cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemPrice);
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.valueOf(Order.Status.PENDING.name()));
        order.setShippingAddress(address);
        order.setTotal(total);
        orderRepository.save(order);

        Set<OrderItem> orderItems = new HashSet<>(shoppingCart.getCartItems().size());
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Book book = cartItem.getBook();

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBook(book);
            orderItem.setOrder(order);
            orderItem.setPrice(book.getPrice());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        shoppingCartService.clearShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(Long userId, Pageable pageable) {
        return orderMapper.toDtoList(orderRepository.findAllByUserId(userId, pageable));
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long id, String value) {
        Order order = orderRepository.findOrderById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find an order with given id : " + id));
        Order.Status statusToUpdate = null;
        for (Order.Status status : Order.Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                statusToUpdate = status;
            }
        }
        if (statusToUpdate == null) {
            throw new OrderUpdateException("Invalid status: " + value);
        }
        order.setStatus(Order.Status.valueOf(statusToUpdate.name()));
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
