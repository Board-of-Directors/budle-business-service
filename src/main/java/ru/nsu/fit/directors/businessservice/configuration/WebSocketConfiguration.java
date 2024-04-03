package ru.nsu.fit.directors.businessservice.configuration;

import java.util.Map;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/business");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/business/chat")
            .setAllowedOriginPatterns("*")
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor());
        registry.addEndpoint("/business/chat")
            .setAllowedOriginPatterns("*")
            .setHandshakeHandler(handshakeHandler());
    }

    @Bean
    public HandshakeHandler handshakeHandler() {
        return (request, response, wsHandler, attributes) -> {
            if (request instanceof ServletServerHttpRequest servletServerRequest) {
                HttpSession session = servletServerRequest.getServletRequest().getSession();
                log.info(session.getId());
                log.info(servletServerRequest.getHeaders().toString());
                attributes.put("sessionId", session.getId());
                attributes.put("principal", servletServerRequest.getPrincipal());
                Object info = servletServerRequest.getPrincipal();
                if (info instanceof BusinessUser user) {
                    log.info(user.getUsername());
                } else {
                    log.info("No user");
                }
            }
            return true;
        };
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {

        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Map<String, Object> attributes
            ) {
                if (request instanceof ServletServerHttpRequest servletServerRequest) {
                    HttpSession session = servletServerRequest.getServletRequest().getSession();
                    log.info(session.getId());
                    log.info(servletServerRequest.getHeaders().toString());
                    attributes.put("sessionId", session.getId());
                    attributes.put("principal", servletServerRequest.getPrincipal());
                    Object info = servletServerRequest.getPrincipal();
                    if (info instanceof BusinessUser user) {
                        log.info(user.getUsername());
                    } else {
                        log.info("No user");
                    }
                }
                return true;
            }

            @Override
            public void afterHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Exception exception
            ) {
            }
        };
    }
}

