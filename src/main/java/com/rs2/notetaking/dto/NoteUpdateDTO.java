package com.rs2.notetaking.dto;

public class NoteUpdateDTO {

    private int noteId;
    private String title;
    private String content;
    private int labelId;

    public NoteUpdateDTO(int noteId, String title, String content, int labelId) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labelId = labelId;
    }

    public NoteUpdateDTO() {
    }

    public int getNoteId() {
        return noteId;
    }
 
    public void setNodeId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }

    public int getLabelId() {
        return labelId;
    }
 
    public void setContent(int labelId) {
        this.labelId = labelId;
    }


}
