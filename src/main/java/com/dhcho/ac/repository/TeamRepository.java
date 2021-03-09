package com.dhcho.ac.repository;

import com.dhcho.ac.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // NamedQuery Add
    List<Team> findByName(String name);
}
