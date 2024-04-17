package com.demo.security;

import com.demo.entity.User;
import com.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JWTAuthenticationServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private JWTAuthenticationService jwtAuthenticationService;

    private final String testSecretKey = "test_secret_key";
    private final long testExpirationTime = 86400000; // 24 hours in milliseconds

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGenerateToken() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");

        // Act
        String token = jwtAuthenticationService.generateToken(user);

        // Assert
        assertNotNull(token);
    }

    @Test
    public void testValidateToken_ValidToken() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        String token = jwtAuthenticationService.generateToken(user);

        // Act
        boolean isValid = jwtAuthenticationService.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testValidateToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid_token";

        // Act
        boolean isValid = jwtAuthenticationService.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testGetUserFromToken() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        String token = jwtAuthenticationService.generateToken(user);
        Claims claims = Jwts.parser()
                .setSigningKey(testSecretKey)
                .parseClaimsJws(token)
                .getBody();
        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        // Act
        User result = jwtAuthenticationService.getUserFromToken(token);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }
}
