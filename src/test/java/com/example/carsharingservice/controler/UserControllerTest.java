package com.example.carsharingservice.controler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.dto.user.UserRegistrationDto;
import com.example.carsharingservice.dto.user.UserResponseDto;
import com.example.carsharingservice.mapper.UserMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUserInfo() {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = new User();
        UserResponseDto expectedDto = new UserResponseDto();
        when(userService.getByUsername(authentication.getName())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        UserResponseDto resultDto = userController.getUserInfo(authentication);

        assertEquals(expectedDto, resultDto);
    }

    @Test
    void updateUserRole() {
        Long userId = 1L;
        String role = "MANAGER";
        UserResponseDto expectedDto = new UserResponseDto();
        when(userService.updateUserRole(User.Role.MANAGER, userId)).thenReturn(new User());
        when(userMapper.toDto(any())).thenReturn(expectedDto);

        UserResponseDto resultDto = userController.updateUserRole(userId, role);

        assertEquals(expectedDto, resultDto);
    }

    @Test
    void updateUserInfo() {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        UserResponseDto expectedDto = new UserResponseDto();
        when(userMapper.toModel(registrationDto)).thenReturn(new User());
        when(userService.updateUserInfo(any(), any())).thenReturn(new User());
        when(userMapper.toDto(any())).thenReturn(expectedDto);

        UserResponseDto resultDto = userController.updateUserInfo(authentication, registrationDto);

        assertEquals(expectedDto, resultDto);
        verify(authentication).setAuthenticated(false);
    }
}
