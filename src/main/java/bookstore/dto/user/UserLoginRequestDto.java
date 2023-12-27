package bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @Email
    private String email;
    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 8, max = 20,
            message = "Password must be at least 8 characters and 20 characters max")
    private String password;
}
