package com.rs2.notetaking.service;

import com.rs2.notetaking.dto.LabelFilterDTO;
import com.rs2.notetaking.dto.NoteDetailsDTO;
import com.rs2.notetaking.dto.NoteSaveDTO;
import com.rs2.notetaking.dto.NoteUpdateDTO;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.Note;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.entity.NoteLabelId;
import com.rs2.notetaking.repository.LabelRepo;
import com.rs2.notetaking.repository.NoteLabelRepo;
import com.rs2.notetaking.repository.NoteRepo;

import exceptions.AppException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (noteSaveDto.getLabels().isEmpty()) {
            return new NoteDetailsDTO(note.getId(), note.getTitle(), note.getContent(), null);
        }

        List<Label> labels = createLabels(noteSaveDto.getLabels());

        createNewNoteLabels(note, labels);

        return new NoteDetailsDTO(note.getId(), note.getTitle(), note.getContent(), labels);

    }

    /**
     * Get all notes.
     */
    @Override
    public List<NoteDetailsDTO> getAllNotes() {
        List<NoteDetailsDTO> result = new ArrayList<>();

        // Get all notes
        List<Note> allNotes = noteRepo.findAll();

        allNotes.forEach((note) -> {
            List<NoteLabel> findByIdNoteId = noteLabelRepo.findByIdNote(note);

            if (findByIdNoteId.isEmpty()) {
                result.add(new NoteDetailsDTO(note.getId(), note.getTitle(),
                        note.getContent(), null));
            } else {
                List<Label> labels = new ArrayList<>();

                // TODO: Improve the logic for when we need to get all labels with note
                findByIdNoteId.forEach((nl) -> {
                    NoteLabelId noteLabel = nl.getId();
                    labels.add(noteLabel.getLabel());

                });

                result.add(new NoteDetailsDTO(note.getId(), note.getTitle(),
                        note.getContent(), labels));
            }
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
            throw new AppException("Note with id [" + noteUpdateDTO.getNoteId() + "] does not exist",
                    HttpStatus.BAD_REQUEST);
        }

        // Update Note
        Note noteDetails = note.get();
        noteDetails.setTitle(noteUpdateDTO.getTitle());
        noteDetails.setContent(noteUpdateDTO.getContent());
        noteRepo.save(noteDetails);

        // If the label name is not provided
        if (noteUpdateDTO.getLabels().isEmpty()) {
            // If the note to be updated have a label linked to it - need to remove it
            List<NoteLabel> noteLabel = noteLabelRepo.findByIdNote(noteDetails);
            noteLabelRepo.deleteAll(noteLabel);

            return Optional.ofNullable(
                    new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(), noteDetails.getContent(), null));
        }

        // Update/Create Label
        List<Label> labels = createLabels(noteUpdateDTO.getLabels());

        // Save link between label & note
        // If the note to be updated have a label linked to it - just update
        List<NoteLabel> noteLabel = noteLabelRepo.findByIdNote(noteDetails);

        // If the note label link is not found
        if (noteLabel.isEmpty()) {
            // Create new note label
            createNewNoteLabels(noteDetails, labels);
            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(),
                    noteDetails.getContent(), labels));

        } else {
            // Remove any other linked labels with the note
            noteLabelRepo.deleteAll(noteLabel);
            // Create the new label
            createNewNoteLabels(noteDetails, labels);

            return Optional.ofNullable(new NoteDetailsDTO(noteDetails.getId(), noteDetails.getTitle(),
                    noteDetails.getContent(), labels));
        }
    }

    /**
     * Delete a note.
     * 
     * We are making sure that there are no 'unused' labels.
     */
    @Override
    public boolean deleteNote(Long id) {
        Optional<Note> note = noteRepo.findById(id);

        if (!note.isPresent()) {
            throw new AppException("Note with id [" + id + "] does not exist",
                    HttpStatus.BAD_REQUEST);
        }
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

        return true;
    }

    /**
     * Get a list of labels stored in the database
     */
    @Override
    public List<Label> getAllLabels() {
        return labelRepo.findAll();
    }

    /**
     * Search for title, note content or label
     * 
     * Step 1: Search for the text in the notes
     * Step 2: Search for the text in the labels
     * Step 3: Return one list that consolidates the result of Step 1 & Step 2
     * 
     * NOTE: Two separate queries were implemented as a note does not necessarily
     * have a label linked to it.
     */
    @Override
    public List<NoteDetailsDTO> filterNotes(String text) {
        List<NoteDetailsDTO> searchList = new ArrayList<>();

        // Step 1: Check if the text matches any content or title in the Note
        filterNotes(text, searchList);

        // Step2: Check if the text matches any label
        filterLabels(text, searchList);

        // Remove any duplicate notes from the result of the 2 queries
        List<NoteDetailsDTO> searchResult = searchList.stream().distinct().collect(Collectors.toList());

        return searchResult;
    }

    /**
     * Logic to filter notes by labels
     */
    @Override
    public List<NoteLabelId> filterLabels(LabelFilterDTO labelFilter) {
        List<NoteLabel> filterByLabelIds = noteLabelRepo.filterByLabelIds(labelFilter.getLabels());
        List<NoteLabelId> result = new ArrayList<>();

        filterByLabelIds.forEach((noteLabel) -> {
            result.add(noteLabel.getId());
        });
        return result;
    }

    private List<Label> createLabels(List<String> labelsList) {
        List<Label> labelList = new ArrayList<>();

        labelsList.forEach((label) -> {
            List<Label> labels = labelRepo.findByName(label);

            // If the label already exists
            if (!labels.isEmpty()) {
                labelList.addAll(labels);
            } else {
                // Create new label
                Label labelCreated = new Label(label);
                labelRepo.save(labelCreated);
                labelList.add(labelCreated);
            }
        });

        return labelList;
    }

    private void createNewNoteLabels(Note note, List<Label> labels) {
        labels.forEach((label) -> {
            NoteLabelId noteLabelId = new NoteLabelId();
            noteLabelId.setLabel(label);
            noteLabelId.setNote(note);

            NoteLabel newNoteLabel = new NoteLabel(noteLabelId);

            noteLabelRepo.save(newNoteLabel);
        });
    }

    private void filterLabels(String text, List<NoteDetailsDTO> searchList) {
        List<NoteLabel> filterLabels = noteLabelRepo.filterByLabelName(text);

        filterLabels.forEach((note) -> {
            List<NoteLabel> findByIdNoteId = noteLabelRepo.findByIdNote(note.getId().getNote());

            if (!findByIdNoteId.isEmpty()) {
                List<Label> labels = new ArrayList<>();

                // TODO: Improve the logic for when we need to get all labels with note
                findByIdNoteId.forEach((nl) -> {
                    NoteLabelId noteLabel = nl.getId();
                    labels.add(noteLabel.getLabel());

                });

                NoteLabelId noteLabelId = note.getId();
                searchList.add(new NoteDetailsDTO(noteLabelId.getNote().getId(), noteLabelId.getNote().getTitle(),
                        noteLabelId.getNote().getContent(), labels));
            }
        });
    }

    private void filterNotes(String text, List<NoteDetailsDTO> searchList) {
        // Query all the notes that match with the filetered text
        List<Note> filterNotes = noteRepo.filter(text);

        // If exists, get the labels of the filtered notes
        filterNotes.forEach((note) -> {
            // Lookup note
            List<NoteLabel> findByIdNoteId = noteLabelRepo.findByIdNote(note);

            if (findByIdNoteId.isEmpty()) {
                // Case: The note does not have a label assigned to it
                searchList.add(new NoteDetailsDTO(note.getId(), note.getTitle(), note.getContent(), null));

            } else {
                // Case: The note have a label assigned to it
                List<Label> labels = new ArrayList<>();
                findByIdNoteId.forEach((nl) -> {
                    NoteLabelId noteLabel = nl.getId();
                    labels.add(noteLabel.getLabel());

                });

                searchList.add(new NoteDetailsDTO(note.getId(), note.getTitle(), note.getContent(),
                        labels));

            }
        });
    }

}
