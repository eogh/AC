package com.dhcho.ac.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"team"})
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

    @Column(unique = true)
    private String qrcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Builder
    public Member(String name, GenderType gender, Birth birth, Address address, String qrcode, Team team) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.qrcode = qrcode;
        if (team != null) {
            this.team = team;
        }
    }

    public void update(String name, GenderType gender, Birth birth, Address address, String qrcode, Team team) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.qrcode = qrcode;
        if (team != null) {
            this.team = team;
        }
    }
}
