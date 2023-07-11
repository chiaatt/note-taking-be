package com.rs2.notetaking.dto;

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

    public NoteSaveDTO() {
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

    public String getLabelName() {
        return labelName;
    }
 
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

}
