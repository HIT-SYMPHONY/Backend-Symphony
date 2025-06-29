package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.service.AuthorizationService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final ClassroomRepository classroomRepository;

    @Override
    public boolean isClassLeader(Authentication authentication, String classRoomId) {

        String currentUserId = authentication.name();

        ClassRoom classRoom = classroomRepository.findById(classRoomId)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy leader"));
        return classRoom.getLeaderId().equals(currentUserId);
    }
}
