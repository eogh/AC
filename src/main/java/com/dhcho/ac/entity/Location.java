package com.dhcho.ac.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "LOCATION_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String desc;

    @Builder
    public Location(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public void update(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
