package ru.seknera.project.kursovaya;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.seknera.project.kursovaya.exceptions.UsernameAlreadyExistsException;
import ru.seknera.project.kursovaya.model.User;
import ru.seknera.project.kursovaya.model.UserAuthority;
import ru.seknera.project.kursovaya.model.UserRole;
import ru.seknera.project.kursovaya.repository.UserRepository;
import ru.seknera.project.kursovaya.repository.UserRolesRepository;
import ru.seknera.project.kursovaya.service.UserServiceImpl;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRolesRepository userRolesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegistrationSuccess() {
        String username = "testuser";
        String password = "testpassword";
        User user = new User()
                .setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setEnabled(true)
                .setLocked(false)
                .setExpired(false)
                .setUserRoles(Collections.singletonList(new UserRole(null, UserAuthority.USER, null)));

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.registration(username, password);

        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegistrationFailureUsernameExists() {
        String username = "existinguser";
        String password = "testpassword";
        User existingUser = new User().setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registration(username, password));

        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, never()).save(any(User.class));
    }
}
