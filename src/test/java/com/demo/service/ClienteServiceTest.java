package com.demo.service;

import com.demo.dto.ClienteDTO;
import com.demo.entity.Cliente;
import com.demo.excepciones.cliente.*;
import com.demo.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;


    // Add more test cases for other scenarios like ClienteMenorEdadException, NombreApellidoInvalidoException, etc.
}
