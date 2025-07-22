package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.CommentCompetitionRequest;
import my_computer.backendsymphony.domain.dto.response.CommentCompetitionResponse;
import my_computer.backendsymphony.domain.entity.CommentCompetition;
import my_computer.backendsymphony.domain.mapper.CommentCompetitionMapper;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.CommentCompetitionRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.CommentCompetitionService;
import my_computer.backendsymphony.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentCompetitionServiceImpl implements CommentCompetitionService {

    private final CommentCompetitionRepository commentCompetitionRepository;
    private final CommentCompetitionMapper commentCompetitionMapper;
    private final UserService userService;

    @Override
    @Transactional
    public CommentCompetitionResponse create(CommentCompetitionRequest request) {
        CommentCompetition entity = commentCompetitionMapper.toEntity(request);
        CommentCompetition saved = commentCompetitionRepository.save(entity);

        CommentCompetitionResponse response = commentCompetitionMapper.toResponse(saved);
        response.setCreatedByUsername(userService.getCurrentUser().getUsername());
        return response;
    }

    @Override
    public CommentCompetitionResponse delete(String commentId) {
      return null;
    }

    @Override
    public List<CommentCompetitionResponse> getCommentsOfCompetition(String competitionId) {
        return null;
    }
}
