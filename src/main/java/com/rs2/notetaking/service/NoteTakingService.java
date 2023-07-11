package com.rs2.notetaking.service;

import com.rs2.notetaking.dto.LabelFilterDTO;
import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.NoteLabelId;

import java.util.List;
import java.util.Optional;
 
public interface NoteTakingService {
    NoteDetailsDTO addNote(NoteSaveDTO noteSaveDto);
 
    List<NoteDetailsDTO> getAllNotes();
 
    Optional<NoteDetailsDTO> updateNote(NoteUpdateDTO noteUpdateDto);
 
    boolean deleteNote(int id);

    // Ideally implement this in the Label Service
    List<Label> getAllLabels();

    List<NoteLabelId> filterLabels(LabelFilterDTO labelFilter);

    List<NoteDetailsDTO> filterNotes(String filter);
}
