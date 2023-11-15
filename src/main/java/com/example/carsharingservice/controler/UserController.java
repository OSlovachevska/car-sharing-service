package com.example.carsharingservice.controler;

import com.example.carsharingservice.dto.user.UserRegistrationDto;
import com.example.carsharingservice.dto.user.UserResponseDto;
import com.example.carsharingservice.mapper.UserMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    @Operation(description = "get all info about logged in user")
    public UserResponseDto getUserInfo(Authentication authentication) {
        return userMapper.toDto(userService.getByUsername(authentication.getName()));
    }

    @PutMapping("/{id}/role")
    @Operation(description = "change user role by id")
    public UserResponseDto updateUserRole(
            @PathVariable @Parameter(description = "user id") Long id,
            @RequestParam @Parameter(description = "role") String role) {
        return userMapper.toDto(userService.updateUserRole(User.Role.valueOf(role), id));
    }

    @PutMapping("/me")
    @Operation(description = "updates logged in user information")
    public UserResponseDto updateUserInfo(Authentication authentication,
                                          @RequestBody UserRegistrationDto dto) {
        UserResponseDto userResponseDto = userMapper.toDto(
                userService.updateUserInfo(userMapper.toModel(dto), authentication.getName()));
        authentication.setAuthenticated(false);
        return userResponseDto;
    }
}



