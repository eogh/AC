package com.dhcho.ac.dto;

import com.dhcho.ac.entity.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;
    private String name;

    public MemberDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }
}
