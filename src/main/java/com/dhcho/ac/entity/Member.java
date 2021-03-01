package com.dhcho.ac.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private LocalDateTime birthday;
    @Enumerated(EnumType.STRING)
    private BirthdayType birthday_type;
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, Group group) {
        this.name = name;
        if (group != null) {
            changeGroup(group);
        }
    }

    public void changeGroup(Group group) {
        this.group = group;
//        group.getMembers().add(this);
    }
}