package com.dhcho.ac.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Member() {
    }

    public Member(String name) {
        this.name = name;
    }
}
