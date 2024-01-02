package bookstore.service.user;

import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.role.RoleRepository;
import bookstore.repository.user.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User wit given email already exists");
        }
        User user = userMapper.toModel(requestDto);
        Role role = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(requestDto.getRepeatPassword()));
        return userMapper.toDto(userRepository.save(user));
    }
}
