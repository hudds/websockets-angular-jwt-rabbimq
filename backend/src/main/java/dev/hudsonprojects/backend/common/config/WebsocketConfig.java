package dev.hudsonprojects.backend.common.config;

import dev.hudsonprojects.backend.security.JwtService;
import dev.hudsonprojects.backend.security.notification.NotificationSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {


    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketConfig.class);
    public static final String NOTIFICATION_USER_DESTINATION = "/notification/user/";

    private final NotificationSecurityService notificationSecurityService;

    public WebsocketConfig(NotificationSecurityService notificationSecurityService) {
        this.notificationSecurityService = notificationSecurityService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/notification");
        registry.setApplicationDestinationPrefixes("/publish");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/events").setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor == null) {
                    return message;
                }
                String destination = accessor.getDestination();
                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    if (destination == null) {
                        throw new IllegalArgumentException("destination is null");
                    }
                    String token = Optional.ofNullable(accessor.getNativeHeader("token"))
                            .filter(values -> !values.isEmpty())
                            .map(List::getFirst)
                            .map(String::valueOf)
                            .orElse(null);
                    try {
                        if (destination.startsWith(NOTIFICATION_USER_DESTINATION)) {
                            String username = destination.substring(NOTIFICATION_USER_DESTINATION.length());
                            notificationSecurityService.validateToken(token, username);
                        } else {
                            notificationSecurityService.validateToken(token);
                        }
                    } catch (RuntimeException e){
                        LOGGER.error("WebSocket error", e);
                        throw e;
                    }
                }
                return message;
            }
        });
    }
}
