package com.rs2.notetaking.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "note-label")
public class NoteLabel implements Serializable {

    @EmbeddedId
    private NoteLabelId id;

    public NoteLabel() {}

    public NoteLabel(NoteLabelId id) {
        this.id = id;
    }   

}
 
