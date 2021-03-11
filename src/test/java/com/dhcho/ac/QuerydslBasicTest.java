package com.dhcho.ac;

import com.dhcho.ac.entity.GenderType;
import com.dhcho.ac.entity.Member;
import com.dhcho.ac.entity.QMember;
import com.dhcho.ac.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void before() {
        Team teamA = Team.builder()
                .name("teamA")
                .build();
        Team teamB = Team.builder()
                .name("teamB")
                .build();
        em.persist(teamA);
        em.persist(teamB);

        for(int i = 0; i < 2; i++) {
            em.persist(Member.builder()
                .name("member"+i)
                .gender(GenderType.MALE)
                .team(teamA)
                .build());
        }
        for(int i = 2; i < 4; i++) {
            em.persist(Member.builder()
                    .name("member"+i)
                    .gender(GenderType.MALE)
                    .team(teamB)
                    .build());
        }
    }

    @Test
    public void startJPQL() {
        String qlString =
                "select m from Member m " +
                "where m.name = :name";

        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("name", "member1")
                .getSingleResult();

        assertThat(findMember.getName()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        // 오른쪽 Gladle -> Tasks -> other -> complieQuerydsl 실행필요 (QEntity 생성)
        QMember m = new QMember("m");

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.name.eq("member1"))
                .fetchOne();

        assertThat(findMember.getName()).isEqualTo("member1");
    }
}
