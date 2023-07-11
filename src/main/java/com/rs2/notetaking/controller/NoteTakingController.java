package com.rs2.notetaking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs2.notetaking.dto.NoteDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.service.NoteTakingService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/note")
public class NoteTakingController {
     @Autowired
    private NoteTakingService noteService;

    @PostMapping()

    public String saveNote(@RequestBody NoteSaveDTO noteSaveDto)
    {
        String id = noteService.addNote(noteSaveDto);
        return id;
    }

    @GetMapping()
    public List<NoteDTO> getAllNotes()
    {
       List<NoteDTO> allEmployees = noteService.getAllNotes();
       return allEmployees;
    }

    @PutMapping()
    public String updateNote(@RequestBody NoteUpdateDTO noteUpdateDTO)
    {
        String id = noteService.updateNote(noteUpdateDTO);
        return id;
    }

    @DeleteMapping(path = "/{id}")
    public String deleteNote(@PathVariable(value = "id") int id)
    {
        boolean deleteNote = noteService.deleteNote(id);
        return "deleted";
    }
    
}
