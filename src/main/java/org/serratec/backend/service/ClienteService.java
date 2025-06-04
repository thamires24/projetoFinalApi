package org.serratec.backend.service;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.config.MailConfig;
import org.serratec.backend.dto.ClienteRequestDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailConfig mailConfig;

    //Lista os clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

  // essa serve para encontrar cliente pelo ID
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

  // encontra os clientes pelo CPF
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

   // serve para cadastrar um cliente novo
    public Cliente salvar(ClienteRequestDTO dto) throws Exception {
        if (dto.getCpf() == null || dto.getCpf().isEmpty()) {
            throw new Exception("CPF é obrigatório.");
        }
        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(dto.getCpf());
        if (clienteExistente.isPresent()) {
            throw new Exception("CPF já cadastrado.");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) throws Exception {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (!clienteExistente.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + id);
        }
        Cliente cliente = clienteExistente.get();

     
        if (!cliente.getCpf().equals(clienteAtualizado.getCpf())) {
            Optional<Cliente> clienteComCpf = clienteRepository.findByCpf(clienteAtualizado.getCpf());
            if (clienteComCpf.isPresent() && !clienteComCpf.get().getId().equals(id)) {
                throw new Exception("CPF já cadastrado para outro cliente.");
            }
        }

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(cliente);
    }

  // serve para deletar o cliente
    public void deletar(Long id) throws Exception {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (!clienteExistente.isPresent()) {
            throw new Exception("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}