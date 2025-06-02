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
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			System.out.println("Teste1");
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				System.out.println("Teste2");
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.isValidToken(token)) {
			System.out.println(token);
			String username = jwtUtil.getUsername(token);
			System.out.println("Teste2.1");
			UserDetails user = userDetailsService.loadUserByUsername(username);
			System.out.println("Teste3");
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}