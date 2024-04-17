package com.demo.repository;

import com.demo.entity.Cliente;
import com.demo.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByCliente(Cliente cliente);
    // Aquí puedes agregar consultas personalizadas si las necesitas
}
