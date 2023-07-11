package com.rs2.notetaking.repo;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.Note;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.entity.NoteLabelId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface NoteLabelRepo extends JpaRepository<NoteLabel, NoteLabelId> {
     List<NoteLabel> findAll();
     List<NoteLabel> findByIdNoteId(Note note);
     List<NoteLabel> findByIdLabelId(Label label);
}
