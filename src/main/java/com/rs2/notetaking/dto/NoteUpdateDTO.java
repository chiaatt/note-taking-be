package com.rs2.notetaking.dto;

import lombok.Data;

@Data
public class NoteUpdateDTO {

    private int noteId;
    private String title;
    private String content;
    private String labelName = "";

    public NoteUpdateDTO(int noteId, String title, String content, String labelName) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labelName = labelName;
    }

    public NoteUpdateDTO() {}

}
