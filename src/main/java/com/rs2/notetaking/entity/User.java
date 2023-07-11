package com.rs2.notetaking.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal id;
 
    @Column(name = "name", length = 32, nullable = false)
    private String name;
 
    @Column(name = "surname", length = 32, nullable = false)
    private String surname;
  
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
 
    public User() {
    }
}
 
