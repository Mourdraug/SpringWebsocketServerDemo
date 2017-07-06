package org.mordraug.wssecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class CsrfCookieFilter extends OncePerRequestFilter {
	
	/**
	 * Filter that includes csrf token in http response as cookie, 
	 * this is default way that angular2 can handle out of the box;
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		CsrfToken token = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
		if (token != null) {
			Cookie cookie = WebUtils.getCookie(req, "XSRF-TOKEN");
			String tokenString = token.getToken();
			if(cookie==null || tokenString!=null && token.equals(cookie.getValue())){
				cookie = new Cookie("XSRF-TOKEN", tokenString);
				cookie.setPath("/");
				res.addCookie(cookie);
			}
		}
		chain.doFilter(req, res);
	}

}
