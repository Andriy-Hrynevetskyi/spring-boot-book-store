package bookstore.controller;

import bookstore.dto.order.AddOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.dto.orderitem.OrderItemDto;
import bookstore.model.User;
import bookstore.service.order.OrderService;
import bookstore.service.orderitem.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "Add a new order")
    public OrderDto addOrder(@RequestBody AddOrderRequestDto requestDto,
                             Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return orderService.addOrder(user.getId(), requestDto.getShippingAddress());
    }

    @GetMapping
    @Operation(summary = "Get user's orders history")
    public List<OrderDto> getAllOrders(Authentication authentication, Pageable pageable) {
        User user = (User)authentication.getPrincipal();
        return orderService.getAllOrders(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get item list of certain order")
    public List<OrderItemDto> getOrderDetails(@PathVariable Long orderId,
                                              Authentication authentication,
                                              Pageable pageable) {
        User user = (User)authentication.getPrincipal();
        return orderItemService.getAllOrderItems(user.getId(), orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{id}")
    @Operation(summary = "Get certain order item")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long id,
                                     Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return orderItemService.getOrderItemByOrderId(user.getId(), orderId, id);
    }
}
