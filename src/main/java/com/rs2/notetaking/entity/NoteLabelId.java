package com.rs2.notetaking.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable 
@Data
public class NoteLabelId implements Serializable { 
 
    @ManyToOne(optional = false) 
    @JoinColumn(name = "noteid" , nullable = false)
    private Note note;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "labelid", nullable = false)
    private Label label;

    public NoteLabelId() {
    }
    
}