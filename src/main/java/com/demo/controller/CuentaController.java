package com.demo.controller;

import com.demo.dto.CuentaDTO;
import com.demo.entity.Cuenta;
import com.demo.excepciones.cuenta.CuentaNotFoundException;
import com.demo.security.JWTAuthenticationService;
import com.demo.service.CuentaService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/cuentas")
@Api(value = "CuentaController", description = "Operations pertaining to Cuenta in CuentaController")
public class CuentaController {

    private static final Logger logger = LogManager.getLogger(CuentaController.class);

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    // Métodos del controlador

    @ApiOperation(value = "Create a new Cuenta", response = Cuenta.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created Cuenta"),
            @ApiResponse(code = 400, message = "Invalid input provided")
    })
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@ApiParam(value = "CuentaDTO object to create a new Cuenta", required = true) @RequestBody CuentaDTO cuentaDTO,
                                              HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Recibida solicitud para crear una nueva cuenta");
        Cuenta cuenta = cuentaService.crearCuenta(cuentaDTO.getTipoCuenta(), cuentaDTO.getEstado(), cuentaDTO.getSaldo(), cuentaDTO.getExentaGMF(), cuentaDTO.getClienteId());
        return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Consultar una cuenta por su ID", response = Cuenta.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Cuenta"),
            @ApiResponse(code = 404, message = "Cuenta not found")
    })
    @GetMapping("/{cuentaId}")
    public ResponseEntity<Cuenta> consultarCuenta(@ApiParam(value = "ID of the Cuenta to retrieve", required = true) @PathVariable Long cuentaId,
                                                  HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Recibida solicitud para consultar la cuenta con ID: {}", cuentaId);
        try {
            Cuenta cuenta = cuentaService.consultarCuenta(cuentaId);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (CuentaNotFoundException ex) {
            logger.error("No se encontró la cuenta durante la consulta. Detalles: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Eliminar una cuenta por su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted Cuenta"),
            @ApiResponse(code = 400, message = "Invalid input provided"),
            @ApiResponse(code = 404, message = "Cuenta not found")
    })
    @DeleteMapping("/{cuentaId}")
    public ResponseEntity<Void> eliminarCuenta(@ApiParam(value = "ID of the Cuenta to delete", required = true) @PathVariable Long cuentaId,
                                               HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Recibida solicitud para eliminar la cuenta con ID: {}", cuentaId);
        try {
            cuentaService.eliminarCuenta(cuentaId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CuentaNotFoundException ex) {
            logger.error("No se encontró la cuenta durante la eliminación. Detalles: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

