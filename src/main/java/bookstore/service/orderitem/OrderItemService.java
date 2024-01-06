package bookstore.service.orderitem;

import bookstore.dto.orderitem.OrderItemDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems(Long id, Long orderId, Pageable pageable);

    OrderItemDto getOrderItemByOrderId(Long userId, Long orderId, Long itemId);
}
