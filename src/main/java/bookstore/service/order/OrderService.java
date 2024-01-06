package bookstore.service.order;

import bookstore.dto.order.OrderDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto addOrder(Long id, String address);

    List<OrderDto> getAllOrders(Long id, Pageable pageable);
}

