package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.CompetitionUserStatus;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.AddMembersToCompetitionRequest;
import my_computer.backendsymphony.domain.dto.request.JoinCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;
import my_computer.backendsymphony.domain.dto.response.UserResponse;
import my_computer.backendsymphony.domain.dto.response.UserSummaryResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CompetitionUserMapper;
import my_computer.backendsymphony.domain.mapper.UserMapper;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.CompetitionUserRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.CompetitionUserService;
import my_computer.backendsymphony.service.UserService;
import my_computer.backendsymphony.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompetitionUserServiceImpl implements CompetitionUserService {

    private final CompetitionUserRepository competitionUserRepository;
    private final CompetitionRepository competitionRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompetitionUserMapper competitionUserMapper;

    @Override
    @Transactional
    public CompetitionUserResponse joinCompetition(JoinCompetitionRequest request) {
        String userId = userService.getCurrentUser().getId();
        String competitionId = request.getCompetitionId();

        if (competitionUserRepository.existsByUser_IdAndCompetition_Id(userId, competitionId)) {
            throw new InvalidException(ErrorMessage.CompetitionUser.ALREADY_JOINED);
        }

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));

        if (LocalDateTime.now().isBefore(competition.getStartTime()) || LocalDateTime.now().isAfter(competition.getEndTime())) {
            throw new InvalidException(ErrorMessage.Competition.INVALID_TIME_PERIOD);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        CompetitionUser competitionUser = CompetitionUser.builder()
                .competition(competition)
                .user(user)
                .status(CompetitionUserStatus.REGISTERED)
                .joinedAt(LocalDateTime.now())
                .build();

        return competitionUserMapper.toResponse(competitionUserRepository.save(competitionUser));
    }


    @Override
    @Transactional
    public List<CompetitionUserResponse> addMembersToCompetition(AddMembersToCompetitionRequest request) {
        String competitionId = request.getCompetitionId();
        List<String> userIds = request.getUserIds();

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));

        if (LocalDateTime.now().isBefore(competition.getStartTime()) ||
                LocalDateTime.now().isAfter(competition.getEndTime())) {
            throw new InvalidException(ErrorMessage.Competition.INVALID_TIME_PERIOD);
        }

        List<CompetitionUser> competitionUsers = userIds.stream()
                .map(userId -> {
                    if (competitionUserRepository.existsByUser_IdAndCompetition_Id(userId, competitionId)) {
                        throw new InvalidException(ErrorMessage.CompetitionUser.ALREADY_JOINED);
                    }

                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

                    return CompetitionUser.builder()
                            .competition(competition)
                            .user(user)
                            .status(CompetitionUserStatus.REGISTERED)
                            .joinedAt(LocalDateTime.now())
                            .build();
                })
                .toList();

        List<CompetitionUser> savedList = competitionUserRepository.saveAll(competitionUsers);
        return competitionUserMapper.toResponseList(savedList);
    }

    @Override
    @Transactional
    public List<CompetitionUserResponse> removeMembersFromCompetition(AddMembersToCompetitionRequest request) {

        String competitionId = request.getCompetitionId();
        List<String> userIds = request.getUserIds();

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));

        if (LocalDateTime.now().isBefore(competition.getStartTime()) ||
                LocalDateTime.now().isAfter(competition.getEndTime())) {
            throw new InvalidException(ErrorMessage.Competition.INVALID_TIME_PERIOD);
        }

        List<CompetitionUser> removedUsers = userIds.stream()
                .map(userId -> {
                    CompetitionUser cu = competitionUserRepository
                            .findByUser_IdAndCompetition_Id(userId, competitionId)
                            .orElseThrow(() -> new NotFoundException(ErrorMessage.CompetitionUser.ERR_NOT_FOUND));
                     cu.setStatus(CompetitionUserStatus.NOT_REGISTERED);
                    return cu;
                })
                .toList();

        competitionUserRepository.deleteAll(removedUsers);

        return competitionUserMapper.toResponseList(removedUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<UserSummaryResponse> getMembersCompetition(String competitionId, PaginationRequestDto request) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));
        UserResponse currentUser = userService.getCurrentUser();
        if(currentUser.getRole() != Role.ADMIN && !Objects.equals(competition.getCompetitionLeaderId(), currentUser.getId())) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<User> userPage = competitionUserRepository.findUsersByCompetitionId(competitionId, pageable);
        List<UserSummaryResponse> responseList = userMapper.toUserSummaryResponseList(userPage.getContent());
        PagingMeta meta = PaginationUtil.buildPagingMeta(request, userPage);

        return new PaginationResponseDto<>(meta, responseList);
    }


    @Override
    public PaginationResponseDto<UserSummaryResponse> getNonMembersCompetition(String competitionId, PaginationRequestDto request) {
        return null;
    }
}
