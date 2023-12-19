package bookstore.dto.user;

import bookstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(message = "Passwords don't match")
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 8, max = 20,
            message = "Password must be at least 8 characters and 20 characters max")
    private String password;
    @NotBlank(message = "Please reenter your password")
    @Size(min = 8, max = 20,
            message = "Repeat password field must be at least 8 characters and 20 characters max")
    private String repeatPassword;
    @NotBlank(message = "First name cannot be empty or null")
    @Size(max = 255)
    private String firstName;
    @NotBlank(message = "Last name cannot be empty or null")
    @Size(max = 255)
    private String lastName;
    private String shippingAddress;
}
