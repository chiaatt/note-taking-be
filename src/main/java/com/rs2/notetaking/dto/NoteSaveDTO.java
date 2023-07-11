package com.rs2.notetaking.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NoteSaveDTO {

    private String title;
    private String content;
    private List<String> labels = new ArrayList<>();


    public NoteSaveDTO(String title, String content, List<String> labels) {
        this.title = title;
        this.content = content;
        this.labels = labels;
    }
   
    public NoteSaveDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteSaveDTO() {}
}
