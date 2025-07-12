package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.CompetitionCreationRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.mapper.CompetitionMapper;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.service.CompetitionService;
import my_computer.backendsymphony.util.PaginationUtil;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {
    CompetitionRepository competitionRepository;
    CompetitionMapper competitionMapper;
    UploadFileUtil uploadFileUtil;

    @Override
    public CompetitionResponse createCompetition(CompetitionCreationRequest request, MultipartFile imageFile) {
        if (request.getStartTime().isAfter(request.getEndTime()))
            throw new InvalidException(ErrorMessage.Competition.START_TIME_MUST_BEFORE_END_TIME);
        Competition competition = competitionMapper.toCompetition(request);
        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            competition.setImage(imageUrl);
        }
        Competition savedCompetition = competitionRepository.save(competition);
        return competitionMapper.toCompetitionResponse(savedCompetition);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<CompetitionResponse> getAllCompetitions(PaginationSortRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.COMPETITION);

        Page<Competition> competitionPage = competitionRepository.findAll(pageable);

        List<CompetitionResponse> dtos = competitionMapper.toCompetitionResponseList(competitionPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.COMPETITION, competitionPage);

        return new PaginationResponseDto<>(meta, dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetitionResponse getCompetitionById(String id) {
        Competition competition = findCompetitionByIdOrElseThrow(id);
        return competitionMapper.toCompetitionResponse(competition);
    }

    private Competition findCompetitionByIdOrElseThrow(String id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID, new String[]{id}));
    }
}
