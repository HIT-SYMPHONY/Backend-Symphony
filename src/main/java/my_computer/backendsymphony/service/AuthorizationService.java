package my_computer.backendsymphony.service;

import org.springframework.security.core.Authentication;

public interface AuthorizationService {
    boolean isClassLeader(Authentication authentication, String classRoomId);
}
