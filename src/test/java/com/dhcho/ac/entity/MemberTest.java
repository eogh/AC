package com.dhcho.ac.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        Group groupA = new Group("groupA");
        Group groupB = new Group("groupB");
        em.persist(groupA);
        em.persist(groupB);

//        Member member1 = new Member("member1", groupA);
//        Member member2 = new Member("member2", groupB);

        em.flush();
        em.clear();
    }
}