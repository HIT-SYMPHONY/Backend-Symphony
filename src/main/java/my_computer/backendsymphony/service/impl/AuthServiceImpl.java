package my_computer.backendsymphony.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.domain.dto.request.LoginRequest;
import my_computer.backendsymphony.domain.dto.response.LoginResponse;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.security.UserPrincipal;
import my_computer.backendsymphony.security.jwt.JwtTokenProvider;
import my_computer.backendsymphony.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getStudentCode(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
            return new LoginResponse(accessToken, refreshToken, userPrincipal.getId(), authentication.getAuthorities());

        } catch (InternalAuthenticationServiceException | BadCredentialsException ex) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_CREDENTIALS);
        }
    }
}
