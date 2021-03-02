package com.dhcho.ac.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime birthday;

    @Enumerated(EnumType.STRING)
    private BirthdayType birthday_type;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private String qrcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, String qrcode) {
        this.name = name;
        this.qrcode = qrcode;
    }

    public Member(String name, String qrcode, Team team) {
        this.name = name;
        this.qrcode = qrcode;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
//        team.getMembers().add(this);
    }
}