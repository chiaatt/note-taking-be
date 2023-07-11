package com.rs2.notetaking.repo;

import com.rs2.notetaking.entity.Note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface NoteRepo extends JpaRepository<Note, Integer> {

    @Query(value = "SELECT * FROM note n  WHERE lower(n.title) LIKE %?1% OR lower(n.content) LIKE %?1%", nativeQuery = true)
    List<Note> filter (String filter);
}
