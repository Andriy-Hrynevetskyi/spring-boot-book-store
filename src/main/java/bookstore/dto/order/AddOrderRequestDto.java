package bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddOrderRequestDto {
    @NotBlank(message = "shipping address can't be null or empty")
    private String shippingAddress;
}
