package com.rs2.notetaking.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "note")
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public Note(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
