package com.rs2.notetaking.service;

import com.rs2.notetaking.dto.NoteDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;

import java.util.List;
 
public interface NoteTakingService {
    String addNote(NoteSaveDTO noteSaveDto);
 
    List<NoteDTO> getAllNotes();
 
    String updateNote(NoteUpdateDTO noteUpdateDto);
 
    boolean deleteNote(int id);
}
