package com.demo.security;


import com.demo.entity.User;
import com.demo.repository.AuthenticationService;


import com.demo.service.UserService;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTAuthenticationService implements AuthenticationService {

    private final String secretKey = "your_secret_key_here";
    private final long expirationTime = 86400000; // 24 horas en milisegundos

    @Autowired
    private UserService userService;

    public String convertUrlSafeBase64ToStandard(String urlSafeBase64) {
        // Reemplaza los caracteres '-' y '_' por '+' y '/' respectivamente
        String base64 = urlSafeBase64.replace('-', '+').replace('_', '/');
        // Añade relleno de '=' al final del string si es necesario
        int padding = base64.length() % 4;
        if (padding > 0) {
            base64 += "====".substring(padding);
        }
        return base64;
    }


    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }


    @Override
    public boolean validateToken(String token) {
        try {
            String standardBase64Token = convertUrlSafeBase64ToStandard(token);
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(standardBase64Token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public User getUserFromToken(String token) {
        String standardBase64Token = convertUrlSafeBase64ToStandard(token);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(standardBase64Token)
                .getBody();

        String username = claims.getSubject();
        return userService.getUserByUsername(username);
    }

}
