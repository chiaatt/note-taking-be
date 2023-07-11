package com.rs2.notetaking.service;

import com.rs2.notetaking.dto.LabelFilterDTO;
import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteSearchDetailsDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.Note;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.entity.NoteLabelId;
import com.rs2.notetaking.repo.LabelRepo;
import com.rs2.notetaking.repo.NoteLabelRepo;
import com.rs2.notetaking.repo.NoteRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteTakingServiceImpl implements NoteTakingService {
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
    public NoteDetailsDTO addNote(NoteSaveDTO noteSaveDto) {
        Note note = new Note(
                noteSaveDto.getTitle(),
                noteSaveDto.getContent());

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
    public List<NoteLabelId> getAllNotes() {
        List<NoteLabel> allNotesWithLabel = noteLabelRepo.findAll();
        List<NoteLabelId> result = new ArrayList<>();

        allNotesWithLabel.forEach((noteLabel) -> {
            result.add(noteLabel.getId());
        });

        return result;
    }

    /**
     * Update an existing note
     * 
     */
    @Override
    public Optional<NoteDetailsDTO> updateNote(NoteUpdateDTO noteUpdateDTO) {
        Optional<Note> note = noteRepo.findById(noteUpdateDTO.getNoteId());

        if (!note.isPresent()) {
            // TODO: Return exception to notify the consumer of the API that the note id is
            // invalid.
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
            List<NoteLabel> noteLabel = noteLabelRepo.findByIdNote(noteDetails);
            noteLabelRepo.deleteAll(noteLabel);

            return Optional.ofNullable(
                    new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(), noteDetails.getContent(), null));
        }

        // Update/Create Label
        Label label = createLabel(noteUpdateDTO.getLabelName());

        // Save link between label & note
        // If the note to be updated have a label linked to it - just update
        List<NoteLabel> noteLabel = noteLabelRepo.findByIdNote(noteDetails);

        // If the note label link is not found
        if (noteLabel.isEmpty()) {
            // Create new note label
            createNewNoteLabel(noteDetails, label);
            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(),
                    noteDetails.getContent(), label.getName()));

        } else {
            // Remove any other linked labels with the note
            noteLabelRepo.deleteAll(noteLabel);
            // Create the new label
            createNewNoteLabel(noteDetails, label);

            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(),
                    noteDetails.getContent(), label.getName()));
        }
    }

    @Override
    public boolean deleteNote(int id) {
        Optional<Note> note = noteRepo.findById(id);

        if (note.isPresent()) {
            // Delete the link between label and note
            List<NoteLabel> noteLabels = noteLabelRepo.findByIdNote(note.get());
            noteLabelRepo.deleteAll(noteLabels);

            // If label is not used anymore - delete it
            noteLabels.forEach((noteLabelValue) -> {
                Label label = noteLabelValue.getId().getLabel();
                List<NoteLabel> linkedLabel = noteLabelRepo.findByIdLabel(label);

                if (linkedLabel.isEmpty()) {
                    labelRepo.delete(noteLabelValue.getId().getLabel());
                }
            });

            // Delete note
            noteRepo.deleteById(id);
        } else {
            // TODO: Throw exceptions to be handled better
            System.out.println("Note id not found");
        }

        return true;
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRepo.findAll();
    }

    private Label createLabel(String labelName) {
        Label label;

        List<Label> labels = labelRepo.findByName(labelName);

        // If the label already exists
        if (!labels.isEmpty()) {
            // Get the first one - assuming the label names are unique so the size of the
            // list is going to always be one.
            label = labels.get(0);
        } else {
            // Create new label
            label = new Label(labelName);
            labelRepo.save(label);
        }

        return label;
    }

    private void createNewNoteLabel(Note note, Label label) {
        NoteLabelId noteLabelId = new NoteLabelId();
        noteLabelId.setLabel(label);
        noteLabelId.setNote(note);

        NoteLabel newNoteLabel = new NoteLabel(noteLabelId);

        noteLabelRepo.save(newNoteLabel);
    }

    /**
     * Search for title, note content or label
     * 
     * Step 1: Search for the text in the notes
     * Step 2: Search for the text in the labels
     * Step 3: Return one list that consolidates the result of Step 1 & Step 2
     * 
     * NOTE: Two separate queries were implemented as a note does not necessarily have a label linked to it.
     */
    @Override
    public List<NoteSearchDetailsDTO> filterNotes(String text) {
        List<NoteSearchDetailsDTO> searchList = new ArrayList<>();

        // Step 1: Check if the text matches any content or title in the Note
        filterNotes(text, searchList);

        // Step2: Check if the text matches any label
        filterLabels(text, searchList);

        // Remove any duplicate notes from the result of the 2 queries
        List<NoteSearchDetailsDTO> searchResult = searchList.stream().distinct().collect(Collectors.toList());

        return searchResult;
    }

    private void filterLabels(String text, List<NoteSearchDetailsDTO> searchList) {
        List<NoteLabel> filterLabels = noteLabelRepo.filterByLabelName(text);

        filterLabels.forEach((noteLabel) -> {
            NoteLabelId noteLabelId = noteLabel.getId();
            searchList.add(new NoteSearchDetailsDTO(noteLabelId.getNote().getId(), noteLabelId.getNote().getTitle(),
                    noteLabelId.getNote().getContent(), noteLabelId.getLabel().getName(),
                    noteLabelId.getLabel().getId()));
        });
    }

    private void filterNotes(String text, List<NoteSearchDetailsDTO> searchList) {
        // Query all the notes that match with the filetered text
        List<Note> filterNotes = noteRepo.filter(text);

        // If exists, get the labels of the filtered notes
        filterNotes.forEach((note) -> {
            // Lookup note in notelabelrepo
            List<NoteLabel> findByIdNoteId = noteLabelRepo.findByIdNote(note);

            if (findByIdNoteId.isEmpty()) {
                // Case: The note does not have a label assigned to it
                searchList.add(new NoteSearchDetailsDTO(note.getId(), note.getTitle(), note.getContent()));

            } else {
                // TODO: Assuming we have one label assigned to note - this need to be updated
                // to support sending Array of labels
                NoteLabelId noteLabel = findByIdNoteId.get(0).getId();
                searchList.add(new NoteSearchDetailsDTO(note.getId(), note.getTitle(), note.getContent(),
                        noteLabel.getLabel().getName(), noteLabel.getLabel().getId()));
            }
        });
    }

    @Override
    public List<NoteLabelId> filterLabels(LabelFilterDTO labelFilter) {
        List<NoteLabel> filterByLabelIds = noteLabelRepo.filterByLabelIds(labelFilter.getLabels());
        List<NoteLabelId> result = new ArrayList<>();

        filterByLabelIds.forEach((noteLabel) -> {
            result.add(noteLabel.getId());
        });
        return result;
    }
}
