package bookstore.service.orderitem;

import bookstore.dto.orderitem.OrderItemDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems(Long id, Long orderId);

    OrderItemDto getOrderItemByOrderId(Long userId, Long orderId, Long itemId);
}
