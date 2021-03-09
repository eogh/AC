package com.dhcho.ac.repository;

import com.dhcho.ac.entity.Access;
import com.dhcho.ac.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
}
