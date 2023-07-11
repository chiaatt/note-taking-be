package com.rs2.notetaking.dto;

import lombok.Data;

@Data
public class NoteDTO {

    private int noteId;
    private String title;
    private String content;
    private int labelId;

    public NoteDTO(int noteId, String title, String content, int labelId) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labelId = labelId;
    }

    public NoteDTO() {
    }
}
