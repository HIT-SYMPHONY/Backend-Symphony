package my_computer.backendsymphony.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.exception.UnauthorizedException;
import my_computer.backendsymphony.security.jwt.JwtTokenProvider;
import my_computer.backendsymphony.service.CustomUserDetailsService;
import my_computer.backendsymphony.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketJwtAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Message<?> preSend(@NonNull Message<?> message,@NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                try {
                    if (jwtTokenProvider.validateToken(token)) {
                        String username = jwtTokenProvider.getUsernameFromToken(token);
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        accessor.setUser(authentication);
                        log.info("WebSocket connection successful with user {}", username);
                    }
                    else {
                        throw new AuthenticationCredentialsNotFoundException("Invalid JWT Token");
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                    throw new AuthenticationCredentialsNotFoundException("Missing or Malformed Token");
                }
            }
        }
        return message;
    }
}