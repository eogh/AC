package com.dhcho.ac.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType gender;

    @Embedded
    private Birth birth;

    @Embedded
    private Address address;

    private String qrcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member(String name, GenderType gender) {
        this.name = name;
        this.gender = gender;
    }

    public Member(String name, GenderType gender, Birth birth, Address address, String qrcode) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.qrcode = qrcode;
    }

    public Member(String name, GenderType gender, Address address, String qrcode) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.qrcode = qrcode;
    }

    public Member(String name, GenderType gender, Birth birth, Address address, String qrcode, Team team) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
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
