package bookstore.service.orderitem;

import bookstore.dto.orderitem.OrderItemDto;
import bookstore.mapper.OrderItemMapper;
import bookstore.repository.orderitem.OrderItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDto> getAllOrderItems(Long userId, Long orderId) {
        return orderItemMapper.toDtoList(
                orderItemRepository.getAllByOrderIdAndUserId(userId, orderId
                ));
    }
}
