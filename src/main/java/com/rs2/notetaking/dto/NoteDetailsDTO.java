package com.rs2.notetaking.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class NoteDetailsDTO {

    private BigDecimal noteId;
    private String title;
    private String content;
    private String labelName = "";

    public NoteDetailsDTO(BigDecimal noteId, String title, String content, String labelName) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labelName = labelName;
    }
   
}
