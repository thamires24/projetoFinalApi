package org.serratec.backend.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
			if (authenticationToken != null) {
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.isValidToken(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (userDetails != null) {
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			}
		}
		return null;
	}
}