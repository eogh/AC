package com.dhcho.ac.api;

import com.dhcho.ac.entity.Member;
import com.dhcho.ac.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberRepository memberRepository;

    @GetMapping("/api/v1/members")
    public Result membersV1() {
        List<Member> findMembers = memberRepository.findAll();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());

        return new Result(collect);
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;

        public MemberDto(Member member) {
            this.name = member.getName();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
