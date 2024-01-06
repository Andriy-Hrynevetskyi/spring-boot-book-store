package bookstore.service.orderitem;

import bookstore.dto.orderitem.OrderItemDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.OrderItemMapper;
import bookstore.model.OrderItem;
import bookstore.repository.orderitem.OrderItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDto> getAllOrderItems(Long userId, Long orderId, Pageable pageable) {
        return orderItemMapper.toDtoList(
                orderItemRepository.findAllByOrderIdAndUserId(userId, orderId
                ));
    }

    @Override
    public OrderItemDto getOrderItemByOrderId(Long userId, Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findOrderItemByIdAndOrderIdAndUserId(
                userId, orderId, itemId)
                .orElseThrow(() -> new EntityNotFoundException("No such order item"));
        return orderItemMapper.toDto(orderItem);
    }
}
