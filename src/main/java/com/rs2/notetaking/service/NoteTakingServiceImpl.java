package com.rs2.notetaking.service;
import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.Note;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.entity.NoteLabelId;
import com.rs2.notetaking.repo.LabelRepo;
import com.rs2.notetaking.repo.NoteLabelRepo;
import com.rs2.notetaking.repo.NoteRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteTakingServiceImpl implements NoteTakingService
{
    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private NoteLabelRepo noteLabelRepo;
 
    /**
     * Logic to add a new note.
     * 
     * Adds a new note together with the corresponding label if provided.
     * If a label did not exists before, a new label is created.
     * 
     */
    @Override
    public NoteDetailsDTO addNote(NoteSaveDTO noteSaveDto)
    {
        Note note = new Note(
            noteSaveDto.getTitle(),
            noteSaveDto.getContent()
        );

        noteRepo.save(note);
        
        // If the label name is not provided
        if (noteSaveDto.getLabelName().isEmpty()) {
            return new NoteDetailsDTO(note.getId(), note.getTitle(), note.getContent(), null);
        } 

        Label label = createLabel(noteSaveDto.getLabelName());

        createNewNoteLabel(note, label);

        return new NoteDetailsDTO(note.getId(), note.getTitle(), note.getContent(), label.getName());
    
    }
 
    /**
     * Get all notes.
     */
    @Override
    public List<NoteLabel> getAllNotes() {
        List<NoteLabel> allNotesWithLabel = noteLabelRepo.findAll();

        return allNotesWithLabel;
    }
 
    /**
     * Update an existing note
     * 
     * Assuming: One note is
     */
    @Override
    public Optional<NoteDetailsDTO> updateNote(NoteUpdateDTO noteUpdateDTO)
    {
        Optional<Note> note = noteRepo.findById(noteUpdateDTO.getNoteId());
        
        if (!note.isPresent()) {
            //TODO: Return exception to notify the consumer of the API that the note id is invalid.
            return null;
        }

        // Update Note
        Note noteDetails = note.get();
        noteDetails.setTitle(noteUpdateDTO.getTitle());
        noteDetails.setContent(noteUpdateDTO.getContent());
        noteRepo.save(noteDetails);

        // If the label name is not provided
        if (noteUpdateDTO.getLabelName().isEmpty()) {
            // If the note to be updated have a label linked to it - need to remove it
            List<NoteLabel> noteLabel = noteLabelRepo.findByIdNoteId(noteDetails);
            noteLabelRepo.deleteAll(noteLabel);

            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(), noteDetails.getContent(), null));
        } 

        // Update/Create Label
        Label label = createLabel(noteUpdateDTO.getLabelName());

        // Save link between label & note
        // If the note to be updated have a label linked to it - just update
        List<NoteLabel> noteLabel = noteLabelRepo.findByIdNoteId(noteDetails);

        // If the note label link is not found
        if (noteLabel.isEmpty()) {
            // Create new note label
            createNewNoteLabel(noteDetails, label);
            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(), noteDetails.getContent(), label.getName()));

        } else {
            // Remove any other linked labels with the note
            noteLabelRepo.deleteAll(noteLabel);
            // Create the new label
            createNewNoteLabel(noteDetails, label);

            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(), noteDetails.getContent(), label.getName()));
        }       
    }


    @Override
    public boolean deleteNote(int id) {
        Optional<Note> note = noteRepo.findById(id);
 
        if (note.isPresent()) {
            // Delete the link between label and note
            List<NoteLabel> noteLabels = noteLabelRepo.findByIdNoteId(note.get());
            noteLabelRepo.deleteAll(noteLabels);

            // If label is not used anymore - delete it
            noteLabels.forEach((noteLabelValue) -> {
                Label label = noteLabelValue.getId().getLabelId();
                List<NoteLabel> linkedLabel = noteLabelRepo.findByIdLabelId(label);

                if (linkedLabel.isEmpty()) {
                    labelRepo.delete(noteLabelValue.getId().getLabelId());
                }
            });

            // Delete note
            noteRepo.deleteById(id);
        }
        else {
            //TODO: Throw exceptions to be handled better
            System.out.println("Note id not found");
        }
 
        return true;
    }

    private Label createLabel(String labelName) {
        Label label;

        List<Label> labels = labelRepo.findByName(labelName);

        // If the label already exists
        if (labels.size() > 0) {
            // Get the first one - assuming the label names are unique so the size of the list is going to always be one.
            label = labels.get(0);
        }
        else {
            // Create new label
            label = new Label(labelName);
            labelRepo.save(label);
        }
      
        return label;
    }

    private void createNewNoteLabel(Note note, Label label) {
        NoteLabelId noteLabelId = new NoteLabelId();
        noteLabelId.setLabelId(label);
        noteLabelId.setNoteId(note);

        NoteLabel newNoteLabel = new NoteLabel(noteLabelId);

        noteLabelRepo.save(newNoteLabel);
    }
}
