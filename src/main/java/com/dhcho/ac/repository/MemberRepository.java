package com.dhcho.ac.repository;

import com.dhcho.ac.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // NamedQuery Add
    List<Member> findByName(String name);

    // 실무에서는 주로 지정해서 사용함.
    @Query("select m from Member m where m.id != :id and m.qrcode = :qrcode")
    List<Member> findByQrcode(@Param("id") String id, @Param("qrcode") String qrcode);
}
