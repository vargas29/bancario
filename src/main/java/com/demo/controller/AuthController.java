package com.demo.controller;


import com.demo.entity.User;
import com.demo.security.JWTAuthenticationService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Supongamos que tienes un método en tu servicio de usuario para verificar las credenciales
        // Aquí deberías validar las credenciales del usuario
        if (userService.validateUser(user.getUsername(), user.getPassword())) {
            return authenticationService.generateToken(userService.getUserByUsername(user.getUsername()));
        } else {
            return "Credenciales inválidas";
        }
    }
}
