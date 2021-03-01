package com.dhcho.ac.controller;

import com.dhcho.ac.dto.MemberDto;
import com.dhcho.ac.entity.Member;
import com.dhcho.ac.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public Member findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member;
    }

    @GetMapping("/members2/{id}")
    public Member findMember2(@PathVariable("id") Member member) {
        return member;
    }

    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> new MemberDto(member));
        return map;

//        return memberRepository.findAll(pageable).map(MemberDto::new); // 동일 코드
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i));
        }
    }
}
