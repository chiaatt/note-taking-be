package com.rs2.notetaking.dto;

import lombok.Data;

@Data
public class NoteSaveDTO {

    private String title;
    private String content;
    private String labelName = "";

    public NoteSaveDTO(String title, String content, String labelName) {
        this.title = title;
        this.content = content;
        this.labelName = labelName;
    }

    public NoteSaveDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteSaveDTO() {}
}
