package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long ID = 1L;
    private static final String EMAIL = "test@gmail.com";

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Get user by username")
    void shouldGetUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setEmail(EMAIL);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole(User.Role.CUSTOMER);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        User result = userService.getByUsername(EMAIL);
        assertEquals(user, result);
    }

    @Test
    @DisplayName("update user role")
    public void updateUserRole() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setRole(User.Role.CUSTOMER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUserRole(User.Role.CUSTOMER, userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));

        assertAll(
                () -> assertEquals(userId, updatedUser.getId()),
                () -> assertEquals(User.Role.CUSTOMER, updatedUser.getRole())
        );
    }

    @Test
    @DisplayName("Find users by role")
    void findUserByRole() {
        User.Role role = User.Role.CUSTOMER;
        List<User> expectedUsers = Arrays.asList(new User(), new User());

        when(userRepository.getUserByRole(role)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findUserByRole(role);

        verify(userRepository, times(1)).getUserByRole(role);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    @DisplayName("Save user")
    void saveUser() {
        User userToSave = new User();

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.save(userToSave);

        verify(userRepository, times(1)).save(userToSave);
        assertNotNull(savedUser);
    }

    @Test
    @DisplayName("Get user by ID")
    void getById() {
        Long userId = 1L;
        User expectedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getById(userId);

        verify(userRepository, times(1)).findById(userId);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Get user by ID - User not found")
    void getByIdUserNotFoundTest() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.getByUsername(EMAIL));
    }
}
