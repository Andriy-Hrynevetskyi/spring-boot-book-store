package bookstore.repository.orderitem;

import bookstore.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
        FROM OrderItem oi
        JOIN FETCH oi.book
        JOIN FETCH oi.order o
        JOIN FETCH o.user u
        WHERE o.id = :orderId
        AND u.id = :userId
            """)
    List<OrderItem> getAllByOrderIdAndUserId(Long userId, Long orderId);
}
