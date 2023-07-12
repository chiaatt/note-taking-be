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

import com.rs2.notetaking.dto.LabelFilterDTO;
import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.dto.SearchDTO;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.NoteLabelId;
import com.rs2.notetaking.service.NoteTakingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/note")
public class NoteTakingController {
    @Autowired
    private NoteTakingService noteService;

    @PostMapping()
    public NoteDetailsDTO saveNote(@RequestBody @Valid NoteSaveDTO noteSaveDto) {
        return noteService.addNote(noteSaveDto);
    }

    @GetMapping()
    public List<NoteDetailsDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PutMapping()
    public Optional<NoteDetailsDTO> updateNote(@RequestBody @Valid NoteUpdateDTO noteUpdateDTO) {
        return noteService.updateNote(noteUpdateDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteNote(@PathVariable(value = "id") Long id) {
        noteService.deleteNote(id);
    }

    @PostMapping("/search")
    public List<NoteDetailsDTO> search(@RequestBody @Valid SearchDTO search) {
        return noteService.filterNotes(search.getText().toLowerCase());
    }

    @PostMapping("/filter")
    public List<NoteDetailsDTO> labelFilter(@RequestBody @Valid LabelFilterDTO labelFilter) {
        return noteService.filterLabels(labelFilter);
    }
 
    /**
     * TODO: Ideally this is in the label controller
     */
    @GetMapping("/label")
    public List<Label> getAllLabels() {
        return noteService.getAllLabels();
    }

}
