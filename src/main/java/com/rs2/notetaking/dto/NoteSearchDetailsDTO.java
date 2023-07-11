package com.rs2.notetaking.dto;

import lombok.Data;

@Data
public class NoteSearchDetailsDTO {

    private int noteId;
    private String title;
    private String content;
    private String labelName;
    public Integer labelId = null;

    public NoteSearchDetailsDTO(int noteId, String title, String content, String labelName, Integer labelId) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labelName = labelName;
        this.labelId = labelId;
    }

     public NoteSearchDetailsDTO(int noteId, String title, String content) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
    }
   
}
