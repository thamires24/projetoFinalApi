package org.serratec.backend.config;

import java.util.Arrays;

import org.serratec.backend.security.JwtAuthenticationFilter;
import org.serratec.backend.security.JwtAuthorizationFilter;
import org.serratec.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = authenticationManager(
				http.getSharedObject(AuthenticationConfiguration.class));

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(authz -> authz.requestMatchers("/auth/**", "/login").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
						.requestMatchers("/h2-console/**").permitAll()

						.requestMatchers(HttpMethod.POST, "/clientes").permitAll()
						.requestMatchers(HttpMethod.GET, "/clientes/meus-dados").hasRole("CLIENTE")

						.requestMatchers(HttpMethod.PUT, "/clientes/meus-dados").hasRole("CLIENTE")

						.requestMatchers(HttpMethod.GET, "/produtos/**", "/produtos").permitAll()
						.requestMatchers(HttpMethod.POST, "/produtos/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.GET, "/categorias/**", "/categorias").permitAll()
						.requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.POST, "/pedidos").hasRole("CLIENTE")
						.requestMatchers(HttpMethod.GET, "/pedidos/meus-pedidos").hasRole("CLIENTE")
						.requestMatchers(HttpMethod.GET, "/pedidos/{id}").hasAnyRole("CLIENTE", "ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/pedidos/{id}/status").hasRole("ADMIN")

						.requestMatchers(HttpMethod.GET, "/enderecos/cep/{cep}").permitAll()

						.requestMatchers("/devolucoes/**").hasRole("CLIENTE").requestMatchers("/wishlist/**")
						.hasRole("CLIENTE")

						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

		http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtUtil),
				UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new JwtAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
				Arrays.asList("http://localhost:3000", "http://localhost:5173", "http://localhost:8081"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "accept",
				"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
		configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Total-Count"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}