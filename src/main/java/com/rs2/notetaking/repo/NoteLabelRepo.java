package com.rs2.notetaking.repo;
import com.rs2.notetaking.entity.Label;
import com.rs2.notetaking.entity.Note;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.entity.NoteLabelId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface NoteLabelRepo extends JpaRepository<NoteLabel, NoteLabelId> {
     List<NoteLabel> findAll();
     List<NoteLabel> findByIdNote(Note note);
     List<NoteLabel> findByIdLabel(Label label);

    // In this case we want the notes that have a label which matches the filter
    @Query(value = "SELECT nl.* FROM \"note-label\" nl INNER JOIN label n on nl.labelid = n.id WHERE lower(n.name) LIKE %?1%", nativeQuery = true)
    List<NoteLabel> filter(String filter);
}
