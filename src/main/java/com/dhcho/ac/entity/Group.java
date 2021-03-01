package com.dhcho.ac.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;
    private String name;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

//    @OneToMany(mappedBy = "group")
//    private List<Member> members = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }
}
