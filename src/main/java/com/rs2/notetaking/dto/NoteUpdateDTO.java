package com.rs2.notetaking.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NoteUpdateDTO {

    private Long noteId;
    private String title;
    private String content;
    private List<String> labels = new ArrayList<>();


    public NoteUpdateDTO(Long noteId, String title, String content, List<String> labels) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labels = labels;
    }
   
    public NoteUpdateDTO() {}

}
