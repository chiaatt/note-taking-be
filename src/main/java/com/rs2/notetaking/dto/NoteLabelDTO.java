package com.rs2.notetaking.dto;

import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.Note;

import lombok.Data;

@Data
public class NoteLabelDTO {

    private Note note;
    private Label label;
    
    public NoteLabelDTO(Note note, Label label) {
        this.note = note;
        this.label = label;
    }
}
