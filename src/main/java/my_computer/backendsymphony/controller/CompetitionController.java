package my_computer.backendsymphony.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.base.RestApiV1;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.UrlConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.request.CompetitionRequest;
import my_computer.backendsymphony.service.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompetitionController {
    CompetitionService competitionService;

    @PostMapping(UrlConstant.Competition.COMPETITION_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCompetition(
            @Valid @RequestPart("data") CompetitionRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(competitionService.createCompetition(request, imageFile));
    }

    @GetMapping(UrlConstant.Competition.COMPETITION_COMMON)
    public ResponseEntity<?> getAllCompetitions(@ModelAttribute PaginationSortRequestDto request) {
        return VsResponseUtil.success(competitionService.getAllCompetitions(request));
    }

    @GetMapping(UrlConstant.Competition.COMPETITION_ID)
    public ResponseEntity<?> getCompetitionById(@PathVariable String id) {
        return VsResponseUtil.success(competitionService.getCompetitionById(id));
    }

    @DeleteMapping(UrlConstant.Competition.COMPETITION_ID)
    public ResponseEntity<?> deleteCompetition(@PathVariable String id) {
        competitionService.deleteCompetition(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(UrlConstant.Competition.COMPETITION_ID)
    public ResponseEntity<?> updateCompetition(@PathVariable String id,
                                               @Valid @RequestPart("data") CompetitionRequest request,
                                               @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(competitionService.updateCompetition(id, request, imageFile));
    }
}
