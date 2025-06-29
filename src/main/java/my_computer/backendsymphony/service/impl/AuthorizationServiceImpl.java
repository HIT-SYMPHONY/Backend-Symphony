package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.service.AuthorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("authz")
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final ClassroomRepository classroomRepository;


    @Override
    public boolean isClassLeader(Authentication authentication, String classRoomId) {

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        String currentUserId = authentication.getName();


        ClassRoom classRoom = classroomRepository.findById(classRoomId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học với ID: " + classRoomId));

        return classRoom.getLeaderId() != null && classRoom.getLeaderId().equals(currentUserId);
    }
}
