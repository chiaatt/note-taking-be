package com.rs2.notetaking.service;
import com.rs2.notetaking.dto.NoteDTO;
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
    public String addNote(NoteSaveDTO noteSaveDto)
    {
        Note note = new Note(
            noteSaveDto.getTitle(),
            noteSaveDto.getContent()
        );

        noteRepo.save(note);
        
        Label label;
        // If the label name is provided
        if (!noteSaveDto.getLabelName().isEmpty()) {

            // Lookup label
            List<Label> labels = labelRepo.findByName(noteSaveDto.getLabelName());

            // If the label already exists
           if (labels.size() > 0) {
                // Get the first one - assuming the label names are unique so the size of the list is going to always be one.
                label = labels.get(0);
           }
            else {
                // Create new label
                label = new Label(noteSaveDto.getLabelName());
                labelRepo.save(label);
            }
        
            // Save link between label & note
            NoteLabelId noteLabelId = new NoteLabelId();
            noteLabelId.setLabelId(label);
            noteLabelId.setNoteId(note);

            NoteLabel noteLabel = new NoteLabel(noteLabelId);

            noteLabelRepo.save(noteLabel);
        }

        return note.getTitle();
    }
 
    @Override
    public List<NoteDTO> getAllNotes() {
    //    List<Note> getemployees = noteRepo.findAll();
    //    List<NoteDTO> employeeDTOList = new ArrayList<>();
    //    for(Note note:getNotes)
    //    {
    //         NoteDTO employeeDTO = new NoteDTO(
 
    //                note.get,
    //                note.getEmployeename(),
    //                note.getEmployeeaddress(),
    //                note.getMobile()
    //        );
    //        employeeDTOList.add(employeeDTO);
    //    }
 
    //    return  employeeDTOList;
    return null;
    }
 
    @Override
    public String updateNote(NoteUpdateDTO employeeUpdateDTO)
    {
        // if (employeeRepo.existsById(employeeUpdateDTO.getEmployeeid())) {
        //     Employee employee = employeeRepo.getById(employeeUpdateDTO.getEmployeeid());
 
 
        //     employee.setEmployeename(employeeUpdateDTO.getEmployeename());
        //     employee.setEmployeeaddress(employeeUpdateDTO.getEmployeeaddress());
        //     employee.setMobile(employeeUpdateDTO.getMobile());
        //     employeeRepo.save(employee);
        // }
        //     else
        //     {
        //         System.out.println("Employee ID do not Exist");
        //     }
 
        //         return null;

        return null;
        }
 
    @Override
    public boolean deleteNote(int id) {
 
        // if(employeeRepo.existsById(id))
        // {
        //     employeeRepo.deleteById(id);
        // }
        // else
        // {
        //     System.out.println("Employee id not found");
        // }
 
        // return true;
        return true;
    }
}
