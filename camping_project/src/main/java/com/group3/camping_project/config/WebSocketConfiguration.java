package com.group3.camping_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
     registry.addEndpoint("/ws-websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
     config.enableSimpleBroker("/topic");
     config.setApplicationDestinationPrefixes("/app");

    }
}
