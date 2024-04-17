package com.demo.service;

import com.demo.dto.ClienteDTO;
import com.demo.entity.Cliente;
import com.demo.excepciones.cliente.*;
import com.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class ClienteService {
    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(ClienteDTO clienteDTO) {

        logger.info("Creando cliente: {}", clienteDTO.getNumeroIdentificacion());

        Optional<Cliente> clienteExistente = clienteRepository.findByNumeroIdentificacion(clienteDTO.getNumeroIdentificacion());
        if (clienteExistente.isPresent()) {
            throw new ClienteYaExisteException("Ya existe un cliente con la misma cédula.");
        }

        if (clienteDTO.getFechaNacimiento().plusYears(18).isAfter(LocalDate.now())) {
            throw new ClienteMenorEdadException("El cliente es menor de edad.");
        }

        if (clienteDTO.getNombres().length() < 2 || clienteDTO.getApellido().length() < 2) {
            throw new NombreApellidoInvalidoException("El nombre y el apellido deben tener al menos 2 caracteres.");
        }

        String regex = "^(.+)@(.+)$";
        if (!Pattern.compile(regex).matcher(clienteDTO.getCorreoElectronico()).matches()) {
            throw new CorreoElectronicoInvalidoException("El correo electrónico no es válido.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombres(clienteDTO.getNombres());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());

        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, ClienteDTO clienteDTO) {
        logger.info("Actualizando cliente con ID {}: {}", id, clienteDTO.getNumeroIdentificacion());

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }

        Cliente cliente = clienteOptional.get();
        // Actualiza los atributos del cliente aquí

        // Actualiza los atributos del cliente aquí
        cliente.setTipoIdentificacion(clienteDTO.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(clienteDTO.getNumeroIdentificacion());
        cliente.setNombres(clienteDTO.getNombres());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) throws Exception {

        logger.info("Eliminando cliente con ID {}", id);
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }

        Cliente cliente = clienteOptional.get();
        if (cliente.tieneProductosVinculados()) {
            throw new Exception("El cliente tiene productos vinculados y no puede ser eliminado.");
        }

        clienteRepository.delete(cliente);
    }



}
