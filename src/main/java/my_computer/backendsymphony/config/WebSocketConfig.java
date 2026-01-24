package my_computer.backendsymphony.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.exception.CustomWebSocketErrorHandler;
import my_computer.backendsymphony.security.WebSocketJwtAuthChannelInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.messaging.access.intercept.AuthorizationChannelInterceptor;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.messaging.context.SecurityContextChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    WebSocketJwtAuthChannelInterceptor webSocketJwtAuthChannelInterceptor;
    private final ApplicationContext applicationContext;
    private final CustomWebSocketErrorHandler customWebSocketErrorHandler;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        AuthorizationManager<Message<?>> authorizationManager = makeAuthorizationManager();
        AuthorizationChannelInterceptor authzInterceptor = new AuthorizationChannelInterceptor(authorizationManager);
        AuthorizationEventPublisher publisher = new SpringAuthorizationEventPublisher(applicationContext);
        authzInterceptor.setAuthorizationEventPublisher(publisher);
        registration.interceptors(
                webSocketJwtAuthChannelInterceptor,
                new SecurityContextChannelInterceptor(),
                authzInterceptor
        );
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*").withSockJS();
        registry.setErrorHandler(customWebSocketErrorHandler);
    }


    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(128 * 1024);
        registration.setSendBufferSizeLimit(512 * 1024);
        registration.setSendTimeLimit(20 * 1000);
    }

    private AuthorizationManager<Message<?>> makeAuthorizationManager() {
        MessageMatcherDelegatingAuthorizationManager.Builder messages =
                new MessageMatcherDelegatingAuthorizationManager.Builder();

        return messages
                .nullDestMatcher().permitAll()
                .simpSubscribeDestMatchers("/topic/**").authenticated()
                .anyMessage().authenticated()
                .build();
    }
}