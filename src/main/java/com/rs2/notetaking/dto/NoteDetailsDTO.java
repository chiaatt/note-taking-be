package com.rs2.notetaking.dto;

import java.util.List;

import com.rs2.notetaking.entity.Label;

import lombok.Data;

@Data
public class NoteDetailsDTO {

    private Long noteId;
    private String title;
    private String content;
    public List<Label> labels;

    public NoteDetailsDTO(Long noteId, String title, String content, List<Label> labels) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.labels = labels;
    }
}
