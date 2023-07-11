package com.rs2.notetaking.service;

import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.entity.NoteLabel;

import java.util.List;
import java.util.Optional;
 
public interface NoteTakingService {
    NoteDetailsDTO addNote(NoteSaveDTO noteSaveDto);
 
    List<NoteLabel> getAllNotes();
 
    Optional<NoteDetailsDTO> updateNote(NoteUpdateDTO noteUpdateDto);
 
    boolean deleteNote(int id);
}
