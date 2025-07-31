package my_computer.backendsymphony.controller;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.AddMembersToCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.JoinCompetitionRequest;
import my_computer.backendsymphony.service.CompetitionUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class CompetitionUserController {

    private final CompetitionUserService competitionUserService;

    @PostMapping(UrlConstant.CompetitionUser.JOIN)
    public ResponseEntity<?> createCompetitionUser(@RequestBody JoinCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.joinCompetition(request));
    }

    @PostMapping(UrlConstant.CompetitionUser.ADD_MULTIPLE)
    public ResponseEntity<?> addMultipleCompetitionUser(@RequestBody AddMembersToCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.addMembersToCompetition(request));
    }

}
