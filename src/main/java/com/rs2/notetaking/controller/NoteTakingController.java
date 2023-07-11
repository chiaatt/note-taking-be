package com.rs2.notetaking.controller;

import java.util.List;
import java.util.Optional;

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

import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.service.NoteTakingService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/note")
public class NoteTakingController {
     @Autowired
    private NoteTakingService noteService;

    @PostMapping()

    public NoteDetailsDTO saveNote(@RequestBody NoteSaveDTO noteSaveDto)
    {
        return noteService.addNote(noteSaveDto);
    }

    @GetMapping()
    public List<NoteLabel> getAllNotes()
    {
       List<NoteLabel> allEmployees = noteService.getAllNotes();
       return allEmployees;
    }

    @PutMapping()
    public Optional<NoteDetailsDTO> updateNote(@RequestBody NoteUpdateDTO noteUpdateDTO)
    {
        return noteService.updateNote(noteUpdateDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteNote(@PathVariable(value = "id") int id)
    {
        noteService.deleteNote(id);
    }
    
}
