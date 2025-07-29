package my_computer.backendsymphony.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.CompetitionUserStatus;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.CompetitionUserRequest;
import my_computer.backendsymphony.domain.dto.response.CompetitionUserResponse;
import my_computer.backendsymphony.domain.entity.Competition;
import my_computer.backendsymphony.domain.entity.CompetitionUser;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.CompetitionUserMapper;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.CompetitionRepository;
import my_computer.backendsymphony.repository.CompetitionUserRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.CompetitionUserService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompetitionUserServiceImpl implements CompetitionUserService {

    private final CompetitionUserRepository competitionUserRepository;
    private final CompetitionRepository competitionRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CompetitionUserMapper competitionUserMapper;

    @Override
    @Transactional
    public CompetitionUserResponse joinCompetition(CompetitionUserRequest request) {
        String userId = userService.getCurrentUser().getId();
        String competitionId = request.getCompetitionId();

        if (competitionUserRepository.existsByUser_IdAndCompetition_Id(userId, competitionId)) {
            throw new InvalidException(ErrorMessage.CompetitionUser.ALREADY_JOINED);
        }

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Competition.ERR_NOT_FOUND_ID));

        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,new String[]{userId}));

        CompetitionUser competitionUser = CompetitionUser.builder()
                .competition(competition)
                .user(user)
                .status(CompetitionUserStatus.REGISTERED)
                .joinedAt(LocalDateTime.now())
                .build();

        return competitionUserMapper.toResponse(competitionUserRepository.save(competitionUser));
    }
}
