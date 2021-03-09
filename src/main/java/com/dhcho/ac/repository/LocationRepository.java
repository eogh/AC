package com.dhcho.ac.repository;

import com.dhcho.ac.entity.Location;
import com.dhcho.ac.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // NamedQuery Add
    List<Location> findByName(String name);
}
