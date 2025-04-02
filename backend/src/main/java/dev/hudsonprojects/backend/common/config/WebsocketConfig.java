package dev.hudsonprojects.backend.common.config;

import dev.hudsonprojects.backend.security.JwtService;
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

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {


    private final JwtService jwtService;

    public WebsocketConfig(JwtService jwtService) {
        this.jwtService = jwtService;
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
                    if(accessor == null){
                        return message;
                    }
                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        System.out.println("token: "+ accessor.getHeader("token"));
                        // TODO implementar a verificação de token
//                        Map<String, List<Object>> nativeHeaders = (Map<String, List<Object>>) message.getHeaders();
//                        String authorization = String.valueOf(nativeHeaders.get("Authorization").get(0));
//                        authorization = authorization.substring("Bearer ".length());

                    } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//                            String destination = accessor.getDestination();
//                            String username = accessor.getUser().getName();
//                            String prefix = "/notification/user/";
//                            if (destination != null && destination.startsWith(prefix)) {
//                                String targetUserId = destination.substring(prefix.length());
//                                if (!username.equals(targetUserId)) {
//                                    throw new SecurityException("User " + username + " is not authorized to subscribe to " + destination);
//                                }
//                            }
                        }
                    return message;
            }
        });
    }
}
