package com.dhcho.ac.api;

import com.dhcho.ac.entity.*;
import com.dhcho.ac.repository.MemberRepository;
import com.dhcho.ac.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
            findTeam = teamRepository.findById(request.getTeamId()).orElse(null);
        }

        if (request.getQrcode() != null) {
            List<Member> findMembers = memberRepository.findByQrcode(null, request.getQrcode());
            if (!findMembers.isEmpty()) {
                throw new IllegalStateException("이미 존재하는 QR코드 입니다.");
            }
        }

        Member member = Member.builder()
                .name(request.getName())
                .gender(request.getGender())
                .birth((request.getBirth()))
                .address(request.getAddress())
                .qrcode(request.getQrcode())
                .team(findTeam)
                .build();

        memberRepository.save(member);
        return new Result(new MemberDto(member));
    }

    // TODO: 페이징처리 추가(V2)
    @GetMapping("/api/v1/members")
    public Result findMembersV1() {
        List<Member> findMembers = memberRepository.findAll();
        List<MemberDto> collect = findMembers.stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/v2/members")
    public Page<Member> findMembersV2(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members;
    }

    // TODO: 동일한 QR코드에 대한 예외처리
    @PutMapping("/api/v1/members/{id}")
    public Result updateMemberV1(
            @PathVariable("id") Long id,
            @RequestBody @Valid MemberRequest request) {
        Team findTeam = null;
        if (request.getTeamId() != null) {
            findTeam = teamRepository.findById(request.getTeamId()).orElse(null);
        }

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

        findMember.update(
                request.getName(),
                request.getGender(),
                request.getBirth(),
                request.getAddress(),
                request.getQrcode(),
                findTeam);
        memberRepository.flush();

        return new Result(new MemberDto(findMember));
    }

    @DeleteMapping("/api/v1/members/{id}")
    public Result removeMemberV1(@PathVariable("id") Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

        memberRepository.delete(findMember);

        return new Result(new MemberDto(findMember));
    }

    @Data
    static class MemberRequest {
        @NotEmpty
        private String name;
        @NotNull
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
        private String teamName;

        public MemberDto(Member member) {
            this.memberId = member.getId();
            this.name = member.getName();
            this.gender = member.getGender();
            this.birth = member.getBirth();
            this.address = member.getAddress();
            this.qrcode = member.getQrcode();
            if (member.getTeam() != null) {
                this.teamName = member.getTeam().getName();
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
