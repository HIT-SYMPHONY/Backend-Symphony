package my_computer.backendsymphony.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface AuthorizationService {
    boolean isClassLeader(Authentication authentication, String classRoomId);
}
