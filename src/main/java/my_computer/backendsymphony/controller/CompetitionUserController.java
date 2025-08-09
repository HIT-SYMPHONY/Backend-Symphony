package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.request.AddMembersToCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.JoinCompetitionRequest;
import my_computer.backendsymphony.service.CompetitionUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CompetitionUserController {

    private final CompetitionUserService competitionUserService;

    @PostMapping(UrlConstant.CompetitionUser.JOIN)
    public ResponseEntity<?> addMemberToCompetition(@Valid @RequestBody JoinCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.joinCompetition(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(UrlConstant.CompetitionUser.ADD_MULTIPLE)
    public ResponseEntity<?> addMultipleCompetitionUser(@Valid @RequestBody AddMembersToCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.addMembersToCompetition(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(UrlConstant.CompetitionUser.REMOVE_MULTIPLE)
    public ResponseEntity<?> removeMultipleCompetitionUser (@RequestBody AddMembersToCompetitionRequest request) {
        return VsResponseUtil.success(competitionUserService.removeMembersFromCompetition(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    @GetMapping(UrlConstant.CompetitionUser.MEMBERS)
    public ResponseEntity<?> getMembersCompetition(@PathVariable String id,
                                                   @Valid @ModelAttribute PaginationRequestDto paginationRequestDto) {
        return VsResponseUtil.success(competitionUserService.getMembersCompetition(id,paginationRequestDto));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER')")
    @GetMapping(UrlConstant.CompetitionUser.NON_MEMBERS)
    public ResponseEntity<?> getNonMembersCompetition(@PathVariable String id,
                                                   @Valid @ModelAttribute PaginationRequestDto paginationRequestDto) {
        return VsResponseUtil.success(competitionUserService.getNonMembersCompetition(id,paginationRequestDto));
    }

}
