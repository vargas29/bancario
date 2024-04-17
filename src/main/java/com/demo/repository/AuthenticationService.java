package com.demo.repository;

import com.demo.entity.User;

public interface AuthenticationService {
    String generateToken(User user);
    boolean validateToken(String token);
    User getUserFromToken(String token);
}
