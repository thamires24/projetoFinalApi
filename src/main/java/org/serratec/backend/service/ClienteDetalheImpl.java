package org.serratec.backend.service;

import org.serratec.backend.entity.Cliente;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteDetalheImpl implements UserDetailsService {
    private ClienteRepository repository;

    // Injeção de dependência através do construtor passando o repositório
    public ClienteDetalheImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente usuario = repository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("CPF não encontrado"));

        return usuario;
    }

}

