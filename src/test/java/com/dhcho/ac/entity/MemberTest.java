package com.dhcho.ac.entity;

import com.dhcho.ac.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", "12345", teamA);
        Member member2 = new Member("member2", "12345", teamB);
        em.persist(member1);
        em.persist(member2);

        // 초기화
        em.flush();
        em.clear();

        // 확인
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member : " + member);
            System.out.println("member.team : " + member.getTeam());
        }
    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        Member member = new Member("member11");
        memberRepository.save(member);

        Thread.sleep(100);
//        member.setName("member22");

        em.flush(); // @PreUpdate
        em.clear();

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        System.out.println("findMember.getCreated_date = " + findMember.getCreated_date());
        System.out.println("findMember.getLast_modified_date = " + findMember.getLast_modified_date());
    }
}