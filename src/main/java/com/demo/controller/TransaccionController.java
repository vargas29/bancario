package com.demo.controller;

import com.demo.dto.TransaccionDTO;
import com.demo.excepciones.CuentaNoEncontradaException;
import com.demo.excepciones.cuenta.SaldoInsuficienteException;
import com.demo.security.JWTAuthenticationService;
import com.demo.service.TransaccionService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/transacciones")
@Api(value = "TransaccionController", description = "Operations pertaining to Transaccion in TransaccionController")
public class TransaccionController {

    private static final Logger logger = LogManager.getLogger(TransaccionController.class);

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @ApiOperation(value = "Realizar una transacción")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transacción realizada exitosamente"),
            @ApiResponse(code = 400, message = "Solicitud inválida"),
            @ApiResponse(code = 404, message = "Cuenta no encontrada"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @PostMapping("/realizar")
    public ResponseEntity<TransaccionDTO> realizarTransaccion(@ApiParam(value = "TransaccionDTO object to perform a transaction", required = true) @RequestBody TransaccionDTO transaccionDTO,
                                                              HttpServletRequest request) {
        if (!isTokenValid(request)) {
            logger.error("Token JWT no válido");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            TransaccionDTO resultado = transaccionService.realizarTransaccion(transaccionDTO);
            logger.info("Transacción realizada exitosamente");
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (CuentaNoEncontradaException e) {
            logger.error("Cuenta no encontrada", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SaldoInsuficienteException e) {
            logger.error("Saldo insuficiente", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error interno del servidor", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para validar el token JWT
    private boolean isTokenValid(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            return jwtAuthenticationService.validateToken(jwtToken);
        }
        return false;
    }
}
