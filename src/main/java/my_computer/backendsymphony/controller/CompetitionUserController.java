package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.request.AddMembersToCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.CompetitionUserUpdateRequest;
import my_computer.backendsymphony.domain.dto.request.UserFilterRequest;
import my_computer.backendsymphony.service.CompetitionUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CompetitionUserController {

    private final CompetitionUserService competitionUserService;

    @PatchMapping(UrlConstant.CompetitionUser.COMPETITION_USER_COMMON)
    public ResponseEntity<?> updateCompetitionUser(@PathVariable String id, @PathVariable String userId, @RequestBody CompetitionUserUpdateRequest request) {
        return VsResponseUtil.success(competitionUserService.updateCompetitionUser(id, userId, request));
    }

    @PostMapping(UrlConstant.CompetitionUser.COMPETITION_USER_COMMON)
    public ResponseEntity<?> registerToCompetition(@PathVariable String id) {
        return VsResponseUtil.success(HttpStatus.CREATED,
                competitionUserService.registerCompetition(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(UrlConstant.CompetitionUser.ADD_MULTIPLE)
    public ResponseEntity<?> addMultipleCompetitionUser(@Valid @RequestBody AddMembersToCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.addMembersToCompetition(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(UrlConstant.CompetitionUser.REMOVE_MULTIPLE)
    public ResponseEntity<?> removeMultipleCompetitionUser(@RequestBody AddMembersToCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.removeMembersFromCompetition(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    @GetMapping(UrlConstant.CompetitionUser.MEMBERS)
    public ResponseEntity<?> getMembersCompetition(@PathVariable String id,
                                                   @Valid @ModelAttribute UserFilterRequest request) {
        return VsResponseUtil.success(competitionUserService.getMembersCompetition(id, request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    @GetMapping(UrlConstant.CompetitionUser.NON_MEMBERS)
    public ResponseEntity<?> getNonMembersCompetition(@PathVariable String id,
                                                      @Valid @ModelAttribute UserFilterRequest request) {
        return VsResponseUtil.success(competitionUserService.getNonMembersCompetition(id, request));
    }

}
