package my_computer.backendsymphony.exception;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import org.springframework.security.core.AuthenticationException;

@Component
public class CustomWebSocketErrorHandler extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof AuthenticationException || cause instanceof AccessDeniedException) {
            return prepareErrorMessage("Unauthorized: " + cause.getMessage());
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> prepareErrorMessage(String errorMessage) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(errorMessage);
        accessor.setLeaveMutable(true);
        return MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());
    }
}
