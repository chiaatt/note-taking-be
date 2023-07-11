package com.rs2.notetaking.repo;
import com.rs2.notetaking.entity.NoteLabel;
import com.rs2.notetaking.entity.NoteLabelId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface NoteLabelRepo extends JpaRepository<NoteLabel, NoteLabelId> {
}
