package com.rs2.notetaking.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "label")
public class Label {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal id;
 
    @Column(name = "name", length = 32, nullable = false)
    private String name;
 
    public Label(String name) {
        this.name = name;
    }

    public Label() {
    }
}
 
