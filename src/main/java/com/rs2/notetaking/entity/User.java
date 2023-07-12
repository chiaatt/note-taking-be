package com.rs2.notetaking.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name="\"user\"")
public class User implements Serializable {
    @Id
    @Column(name = "id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "surname", length = 32, nullable = false)
    private String surname;

    @Column(name = "login", nullable = false)
    @Size(max = 100)
    private String login;

    @Column(name = "password", nullable = false)
    @Size(max = 100)
    private String password;
}
