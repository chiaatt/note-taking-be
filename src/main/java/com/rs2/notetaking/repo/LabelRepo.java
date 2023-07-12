package com.rs2.notetaking.repo;
import com.rs2.notetaking.entity.Label;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface LabelRepo extends JpaRepository<Label, Long> {

    List<Label> findByName(String name);
}
