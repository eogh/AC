package com.dhcho.ac.controller;

import com.dhcho.ac.entity.Member;
import com.dhcho.ac.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/member")
    public Member addOne(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PostMapping("/members")
    public List<Member> addList(@RequestBody List<Member> members) {
        return memberRepository.saveAll(members);
    }

    @GetMapping("/members/{id}")
    public Member findOne(@PathVariable("id") Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @PutMapping("/members/{id}")
    public Member modifyOne(@RequestBody Member member, @PathVariable("id") Long id) {
        return member;
//        return repository.findById(id)
//                .map(employee -> {
//                    employee.setName(newEmployee.getName());
//                    employee.setRole(newEmployee.getRole());
//                    return repository.save(employee);
//                })
//                .orElseGet(() -> {
//                    newEmployee.setId(id);
//                    return repository.save(newEmployee);
//                });
    }

    @DeleteMapping("/members/{id}")
    public void deleteOne(@PathVariable("id") Long id) {
        memberRepository.deleteById(id);
    }

    @DeleteMapping("/members")
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @PostConstruct
    public void init() {
//        for (int i = 0; i < 100; i++) {
//            memberRepository.save(new Member("user" + i));
//        }
    }
}