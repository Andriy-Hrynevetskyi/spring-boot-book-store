package bookstore.service.order;

import bookstore.dto.order.OrderDto;

public interface OrderService {
    OrderDto addOrder(Long id, String address);
}
