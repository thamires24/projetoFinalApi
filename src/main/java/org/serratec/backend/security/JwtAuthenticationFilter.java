package org.serratec.backend.security;

import java.io.IOException;
import java.util.ArrayList;

import org.serratec.backend.dto.LoginDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter: Tentando autenticação para " + request.getRequestURI());
		try {
			LoginDTO loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					loginDto.getUsername(), loginDto.getPassword(), new ArrayList<>());

			return this.getAuthenticationManager().authenticate(authToken);

		} catch (IOException e) {
			System.err
					.println("JwtAuthenticationFilter: Erro ao ler o corpo da requisição de login: " + e.getMessage());
			throw new RuntimeException("Falha ao autenticar usuário: erro ao processar dados de login.", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("JwtAuthenticationFilter: Autenticação bem-sucedida para " + authResult.getName());
		String username = ((UserDetails) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);

		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.err.println("JwtAuthenticationFilter: Falha na autenticação - " + failed.getMessage());
		super.unsuccessfulAuthentication(request, response, failed); 
	}
}