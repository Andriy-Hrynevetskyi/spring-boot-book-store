package bookstore.service.orderitem;

import bookstore.dto.orderitem.OrderItemDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems(Long id, Long orderId, Pageable pageable);

    OrderItemDto getOrderItemByOrderId(Long userId, Long orderId, Long itemId);
}
