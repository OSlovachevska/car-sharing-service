package com.example.carsharingservice.controler;

import com.example.carsharingservice.dto.user.UserLoginDto;
import com.example.carsharingservice.dto.user.UserRegistrationDto;
import com.example.carsharingservice.dto.user.UserResponseDto;
import com.example.carsharingservice.mapper.UserMapper;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.secutiry.JwtTokenProvider;
import com.example.carsharingservice.service.AuthenticationService;
import com.example.carsharingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/register")
    @Operation(description = "register new user")
    UserResponseDto registerUser(@RequestBody UserRegistrationDto request) {
        User user = authenticationService.register(request.getEmail(), request.getFirstName(),
                request.getLastName(), request.getPassword());
        return userMapper.toDto(user);
    }

    @PostMapping(value = "/login")
    @Operation(description = "login with email and password and you will get JWT token in response")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws AuthenticationException {
        User user = authenticationService.login(userLoginDto.getEmail(),
                userLoginDto.getPassword());
        Set<User.Role> roles = new HashSet<>();
        roles.add(user.getRole());
        String token = jwtTokenProvider.createToken(user.getEmail(), roles);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}
