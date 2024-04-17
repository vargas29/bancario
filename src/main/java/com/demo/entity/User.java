package com.demo.entity;

import lombok.Data;



import jakarta.persistence.*;
@Data

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // Getters y setters
}
