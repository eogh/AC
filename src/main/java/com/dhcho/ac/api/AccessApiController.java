package com.dhcho.ac.api;

import com.dhcho.ac.entity.*;
import com.dhcho.ac.repository.AccessRepository;
import com.dhcho.ac.repository.LocationRepository;
import com.dhcho.ac.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccessApiController {

    private final AccessRepository accessRepository;
    private final LocationRepository locationRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/api/v1/access")
    public Result addAccessV1(@RequestBody @Valid AccessRequest request) {
        Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
        Location findLocation = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소 입니다."));

        Access access = Access.builder()
                .member(findMember)
                .location(findLocation)
                .build();

        accessRepository.save(access);
        return new Result(new AccessDto(access));
    }

    // TODO: 페이징처리 추가
    // TODO: fetch join 이용하여 성능 최적화 하기
    @GetMapping("/api/v1/access")
    public Result findAccessV1() {
        List<Access> findAccessList = accessRepository.findAll();
        List<AccessDto> collect = findAccessList.stream()
                .map(AccessDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

//    // 업데이트 기능은 필요 없음
//    @PutMapping("/api/v1/access/{id}")
//    public Result updateAccessV1(@PathVariable("id") Long id) {
//        return new Result("");
//    }

    @DeleteMapping("/api/v1/access/{id}")
    public Result removeAccessV1(@PathVariable("id") Long id) {
        accessRepository.deleteById(id);
        return new Result("");
    }

    @Data
    static class AccessRequest {
        @NotNull
        private Long memberId;
        @NotNull
        private Long locationId;
    }

    @Data
    static class AccessDto {
        private Long accessId;
        private AccessMemberDto member;
        private AccessLocationDto location;

        public AccessDto(Access access) {
            this.accessId = access.getId();
            this.member = new AccessMemberDto(access.getMember());
            this.location = new AccessLocationDto(access.getLocation());
        }
    }

    @Data
    static class AccessMemberDto {
        private Long memberId;
        private String name;
        private GenderType gender;
        private String qrcode;

        public AccessMemberDto(Member member) {
            this.memberId = member.getId();
            this.name = member.getName();
            this.gender = member.getGender();
            this.qrcode = member.getQrcode();
        }
    }

    @Data
    static class AccessLocationDto {
        private Long locationId;
        private String name;
        private String desc;

        public AccessLocationDto(Location location) {
            this.locationId = location.getId();
            this.name = location.getName();
            this.desc = location.getDesc();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
