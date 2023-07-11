package com.rs2.notetaking.dto;

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
