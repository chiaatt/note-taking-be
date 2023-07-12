package com.rs2.notetaking.repo;
import com.rs2.notetaking.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);
}
