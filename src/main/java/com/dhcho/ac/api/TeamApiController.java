package com.dhcho.ac.api;

import com.dhcho.ac.entity.Team;
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
public class TeamApiController {

    private final TeamRepository teamRepository;

    @PostMapping("/api/v1/teams")
    public Result addTeamV1(@RequestBody @Valid TeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .build();

        validateDuplicateTeam(team); // 동일한 이름에 대한 중복체크
        teamRepository.save(team);

        return new Result(new TeamDto(team));
    }

    // TODO: 페이징처리 보류
    @GetMapping("/api/v1/teams")
    public Result findTeamsV1() {
        List<Team> findTeams = teamRepository.findAll();
        List<TeamDto> collect = findTeams.stream()
                .map(TeamDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PutMapping("/api/v1/teams/{id}")
    public Result updateTeamV1(@PathVariable("id") Long id,
                                @RequestBody @Valid TeamRequest request) {
        Team findTeam = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 입니다."));

        findTeam.update(request.getName());
        teamRepository.flush();

        return new Result(new TeamDto(findTeam));
    }

    // TODO: Team삭제시 (1. 해당 팀에 속한 Member를 지운다) (2. 해당 팀에 속한 Member에 TeamId를 null로 만든다) 선택
    @DeleteMapping("/api/v1/teams/{id}")
    public Result removeTeamV1(@PathVariable("id") Long id) {
        Team findTeam = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 입니다."));

        teamRepository.delete(findTeam);

        return new Result(new TeamDto(findTeam));
    }

    @Data
    static class TeamRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class TeamDto {
        private Long teamId;
        private String name;

        public TeamDto(Team team) {
            this.teamId = team.getId();
            this.name = team.getName();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    private void validateDuplicateTeam(Team team) {
        List<Team> findTeams = teamRepository.findByName(team.getName());

        if (!findTeams.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 팀 입니다.");
        }
    }
}
