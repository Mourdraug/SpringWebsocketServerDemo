package org.mordraug.wssecurity.config;

import org.mordraug.wssecurity.filter.CsrfCookieFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * This is actually key piece of configuration for authentication, spring
	 * security does provide out-of-the-box authorization support for
	 * websockets, but we need to be authenticated for this to work. Simpliest
	 * way to do this is using httpBasic authentication before Upgrade request.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable httpBasic authentication
		http.httpBasic()
				// Because we are going to use angular2 http with Authorization
				// header we need access to OPTIONS method for our login 
				// endpoint preflight request.
				.and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/user").permitAll()
				// Allow anonymous access through http websocket endpoint
				// and http endpoint for receiving csrf token
				.and().authorizeRequests().antMatchers("/stomp", "/csrf").permitAll()
				// protect any other endpoint
				.and().authorizeRequests().anyRequest().authenticated().and()
				//add our custom csrf filter AFTER springs default CsrfFilter()
				.addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class)
				//select our customized csrf token repository
				.csrf().csrfTokenRepository(csrfTokenRepository()).ignoringAntMatchers("/csrf");
	}

	/*
	 * Simple in-memory authentication setup for user "user" and password
	 * "password", authorized as ROLE_USER.
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

}
