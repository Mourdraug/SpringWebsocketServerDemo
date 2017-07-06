package org.mordraug.wssecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/*
 * This is very simple websocket broker config. not really much to comment here. 
 * One thing to note is that this configuration uses simple spring broker implementation.
 * Full implementations like RabbitMQ need different setup.
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/app");
        config.setApplicationDestinationPrefixes("/app");
    }
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp").setAllowedOrigins("*"); //For developement purpouses I allowed all Origins, you might want to change this for production.
	}

}
