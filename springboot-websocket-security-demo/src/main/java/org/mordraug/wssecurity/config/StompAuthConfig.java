package org.mordraug.wssecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class StompAuthConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	/**
	 * Sample authorization config. Allows everyone to read text field, 
	 * but requires one to have ROLE_USER to change it
	 */
	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
		.nullDestMatcher().permitAll()
        .simpSubscribeDestMatchers("/app/text").permitAll() 
        .simpDestMatchers("/app/settext").hasRole("USER")
        .anyMessage().denyAll();
	}
}
