package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.order.OrderDto;
import bookstore.model.Order;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "orderItems", ignore = true)
    Order toModel(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> orders);

    List<Order> toModelList(List<OrderDto> orderDtos);
}
