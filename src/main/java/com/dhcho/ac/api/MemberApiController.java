package com.dhcho.ac.api;

import com.dhcho.ac.entity.*;
import com.dhcho.ac.repository.MemberRepository;
import com.dhcho.ac.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @PostMapping("/api/v1/members")
    public Result addMemberV1(@RequestBody @Valid MemberRequest request) {
        Team findTeam = null;
        if (request.getTeamId() != null) {
            findTeam = teamRepository.findById(request.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 입니다."));
        }

        Member member = new Member(request.getName(), request.getGender(), request.getBirth(), request.getAddress(), request.getQrcode(), findTeam);
        validateDuplicateMember(member);
        memberRepository.save(member);
        return new Result(new MemberDto(member));
    }

    // TODO: 페이징처리 추가
    @GetMapping("/api/v1/members")
    public Result findMembersV1() {
        List<Member> findMembers = memberRepository.findAll();
        List<MemberDto> collect = findMembers.stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @PutMapping("/api/v1/members/{id}")
    public Result updateMemberV1(
            @PathVariable("id") Long id,
            @RequestBody @Valid MemberRequest request) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        findMember.setName(request.getName());
        memberRepository.flush();
        return new Result(new MemberDto(findMember));
    }

    @DeleteMapping("/api/v1/members/{id}")
    public Result removeMemberV1(@PathVariable("id") Long id) {
        memberRepository.deleteById(id);
        return new Result("");
    }

    @Data
    static class MemberRequest {
        @NotEmpty
        private String name;
        private GenderType gender;
        private Birth birth;
        private Address address;
        private String qrcode;
        private Long teamId;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private Long memberId;
        private String name;
        private GenderType gender;
        private Birth birth;
        private Address address;
        private String qrcode;
        private Team team;

        public MemberDto(Member member) {
            this.memberId = member.getId();
            this.name = member.getName();
            this.gender = member.getGender();
            this.birth = member.getBirth();
            this.address = member.getAddress();
            this.qrcode = member.getQrcode();
            this.team = member.getTeam();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
