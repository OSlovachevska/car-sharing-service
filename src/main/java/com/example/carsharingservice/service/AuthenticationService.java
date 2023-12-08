package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import javax.naming.AuthenticationException;

public interface AuthenticationService {

    User register(String email, String firstName, String lastName, String password);

    User login(String login, String password) throws AuthenticationException;
}
