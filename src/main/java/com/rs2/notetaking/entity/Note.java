package com.rs2.notetaking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "note")
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "title", length = 32, nullable = false)
    private String title;

    @Column(name = "content", length = 300, nullable = false)
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note() {
    }

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
