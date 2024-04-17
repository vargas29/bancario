package com.demo.controller;

import com.demo.dto.ClienteDTO;
import com.demo.entity.Cliente;
import com.demo.security.JWTAuthenticationService;
import com.demo.service.ClienteService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/clientes")
@Api(value = "ClienteController", description = "Operations pertaining to Cliente in ClienteController")
public class ClienteController {

    private static final Logger logger = LogManager.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @ApiOperation(value = "Create a new Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created Cliente"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@ApiParam(value = "ClienteDTO object to create a new Cliente", required = true) @RequestBody ClienteDTO clienteDTO,
                                                 HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Received request to create a new Cliente");
        Cliente cliente = clienteService.createCliente(clienteDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated Cliente"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Cliente not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@ApiParam(value = "ID of the Cliente to update", required = true) @PathVariable Long id,
                                                 @ApiParam(value = "ClienteDTO object with updated data", required = true) @RequestBody ClienteDTO clienteDTO,
                                                 HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Cliente cliente = clienteService.updateCliente(id, clienteDTO);

        logger.info("Received request to update Cliente with ID: {}", id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted Cliente"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Cliente not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@ApiParam(value = "ID of the Cliente to delete", required = true) @PathVariable Long id,
                                              HttpServletRequest request) throws Exception {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Received request to delete Cliente with ID: {}", id);
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    // Otros métodos del controlador, como obtener todos los clientes, obtener cliente por ID, etc.
}
