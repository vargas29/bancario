package com.demo.controller;

import com.demo.entity.User;
import com.demo.excepciones.usuario.UserNotFoundException;

import com.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(userList);

        // Act
        List<User> result = userController.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    public void testGetUserById_UserFound() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> responseEntity = userController.getUserById(userId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.getUserById(userId));
    }

    // Aquí puedes agregar más pruebas para los otros métodos del controlador UserController
}
