package bookstore.controller;

import bookstore.dto.order.AddOrderRequestDto;
import bookstore.dto.order.OrderDto;
import bookstore.model.User;
import bookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

    @PostMapping
    @Operation(summary = "Add a new order")
    public OrderDto addOrder(@RequestBody AddOrderRequestDto requestDto,
                             Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return orderService.addOrder(user.getId(), requestDto.getShippingAddress());
    }
}
