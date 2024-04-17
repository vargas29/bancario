package com.demo.service;

import com.demo.entity.User;
import com.demo.excepciones.usuario.UserNotFoundException;
import com.demo.excepciones.usuario.UserValidationException;
import com.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Error al obtener usuario por ID", e);
            throw new UserNotFoundException("No se encontró usuario con ID: " + id);
        }
    }

    public User createUser(User user) {
        try {
            // Validar datos de entrada
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                throw new UserValidationException("El nombre de usuario no puede estar vacío");
            }
            // Aquí podrías realizar más validaciones según tus necesidades

            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error al crear usuario", e);
            throw new UserValidationException("Error al crear usuario: " + e.getMessage());
        }
    }

    public User updateUser(User user) {
        try {
            // Validar datos de entrada y existencia del usuario
            if (user.getId() == null || !userRepository.existsById(user.getId())) {
                throw new UserValidationException("El usuario no existe o el ID es nulo");
            }
            // Aquí podrías realizar más validaciones según tus necesidades

            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar usuario", e);
            throw new UserValidationException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error al eliminar usuario", e);
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    public User getUserByUsername(String username) {
        try {
            return userRepository.getUserByUsername(username);
        } catch (Exception e) {
            LOGGER.error("Error al obtener usuario por nombre de usuario", e);
            throw new UserNotFoundException("No se encontró usuario con nombre de usuario: " + username);
        }
    }

    public boolean validateUser(String username, String password) {
        try {
            return userRepository.validateUser(username, password);
        } catch (Exception e) {
            LOGGER.error("Error al validar usuario", e);
            throw new UserValidationException("Error al validar usuario: " + e.getMessage());
        }
    }}