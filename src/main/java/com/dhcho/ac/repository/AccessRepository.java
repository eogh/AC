package com.dhcho.ac.repository;

import com.dhcho.ac.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository {

    // NamedQuery Add
    List<Access> findByName(String name);
}
