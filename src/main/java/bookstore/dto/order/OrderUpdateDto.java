package bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderUpdateDto {
    @NotBlank(message = "Status field is required")
    private String status;
}
