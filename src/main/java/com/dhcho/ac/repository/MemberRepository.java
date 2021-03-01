package com.dhcho.ac.repository;

import com.dhcho.ac.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
