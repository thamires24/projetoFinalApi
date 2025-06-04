package org.serratec.backend.service;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteDetalheImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com email: " + username));
		return cliente;
	}
}