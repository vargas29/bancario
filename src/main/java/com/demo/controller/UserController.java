package com.demo.controller;

import com.demo.entity.User;
import com.demo.excepciones.usuario.UserNotFoundException;
import com.demo.excepciones.usuario.UserValidationException;
import com.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "Obtener todos los usuarios", notes = "Obtiene una lista de todos los usuarios registrados.")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener usuario por ID", notes = "Obtiene un usuario por su ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            throw new UserNotFoundException("No se encontró usuario con ID: " + id);
        }
    }

    @PostMapping
    @ApiOperation(value = "Crear usuario", notes = "Crea un nuevo usuario.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado exitosamente"),
            @ApiResponse(code = 400, message = "Datos de usuario no válidos")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (UserValidationException e) {
            LOGGER.error("Error al crear usuario", e);
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar usuario por ID", notes = "Elimina un usuario por su ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Usuario eliminado exitosamente"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            LOGGER.error("Error al eliminar usuario", e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/validate")
    @ApiOperation(value = "Validar usuario por nombre de usuario y contraseña", notes = "Valida un usuario por su nombre de usuario y contraseña.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario validado exitosamente"),
            @ApiResponse(code = 400, message = "Credenciales de usuario no válidas")
    })
    public ResponseEntity<Void> validateUser(@RequestParam String username, @RequestParam String password) {
        try {
            if (userService.validateUser(username, password)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (UserValidationException e) {
            LOGGER.error("Error al validar usuario", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
